package iut.group42b.boardgames.server.manager;

import iut.group42b.boardgames.application.ServerApplication;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.impl.auth.*;
import iut.group42b.boardgames.server.network.NetworkServer;
import iut.group42b.boardgames.social.model.ExchangedMessage;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.social.packet.friendship.FriendListPacket;
import iut.group42b.boardgames.social.packet.message.MessageListPacket;
import iut.group42b.boardgames.social.packet.message.SendMessagePacket;
import iut.group42b.boardgames.util.Logger;

import java.net.NetworkInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserManager implements INetworkHandler {

	/* Constants */
	public static final int CHANGE_IGNORED = 0;
	public static final int CHANGE_DONE = 1;
	public static final int CHANGE_ERROR = 2;

	/* Logger */
	private static final Logger LOGGER = new Logger(UserManager.class);

	/* Singleton */
	private static final UserManager INSTANCE = new UserManager();

	/* Constructor */
	private UserManager() {
	}

	/**
	 * Get the UserManager Instance
	 *
	 * @return UserManager Instance
	 */
	public static UserManager get() {
		return INSTANCE;
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		if (packet instanceof UserLoginPacket) {
			UserLoginPacket loginPacket = (UserLoginPacket) packet;

			if (this.login(loginPacket)) {
				UserProfile userProfile = this.findUserByEmail(loginPacket.getEmail());

				handler.setProfile(userProfile);
				handler.queue(new UserAuthentificationSuccessPacket(UserAuthentificationSuccessPacket.SuccessType.LOGGED, userProfile));
			} else {
				handler.setProfile(null);
				handler.queue(new UserAuthentificationErrorPacket(UserAuthentificationErrorPacket.ErrorType.BAD_EMAIL_OR_PASSWORD, loginPacket.getEmail()));
			}
		} else if (packet instanceof UserRegisterPacket) {
			UserRegisterPacket registerPacket = (UserRegisterPacket) packet;

			handler.setProfile(null);

			UserAuthentificationErrorPacket.ErrorType error = this.register(registerPacket);

			if (error == null) {
				handler.queue(new UserAuthentificationSuccessPacket(UserAuthentificationSuccessPacket.SuccessType.REGISTER_COMPLETE, null));
			} else {
				handler.queue(new UserAuthentificationErrorPacket(error));
			}
		} else if (packet instanceof UserSettingsChangePacket) {
			UserSettingsChangePacket changePacket = (UserSettingsChangePacket) packet;

			int[] changed = this.changeUserInfo(handler.getUserProfile(), changePacket);
			UserProfile newUserProfile = this.findUserById(handler.getUserProfile().getId());

			handler.setProfile(newUserProfile);

			handler.queue(new UserSettingsChangedPacket(changed, newUserProfile));
		} else if (packet instanceof FriendListPacket) {
			handler.queue(new FriendListPacket(this.findFriendsByUser(handler.getUserProfile())));
		} else if (packet instanceof MessageListPacket) {
			MessageListPacket messageListPacket = (MessageListPacket) packet;

			int to = messageListPacket.getSenderId();

			handler.queue(new MessageListPacket(to, this.getExchangedMessageWith(handler.getUserProfile().getId(), to)));
		} else if (packet instanceof SendMessagePacket) {
			SendMessagePacket sendMessagePacket = (SendMessagePacket) packet;

			ExchangedMessage message = sendMessagePacket.getMessage();

			int senderId = handler.getUserProfile().getId();
			int receiverId = message.getUserId();

			addMessage(senderId, message);

			handler.queue(new MessageListPacket(senderId, this.getExchangedMessageWith(senderId, receiverId)));

			SocketHandler receiverHandler = ServerApplication.getServer().findSocketHandlerByProfileId(receiverId);
			System.out.println(receiverHandler);
			if (receiverHandler != null) {
				receiverHandler.queue(new MessageListPacket(receiverId, this.getExchangedMessageWith(receiverId, senderId)));
			}
		}
	}

	/**
	 * Update in database user's information, like password, email, username, profile picture path.
	 *
	 * @param userProfile selected User to update.
	 * @param property    password, email, image,username...
	 * @param newValue
	 * @return An Error code : 1 for DONE and 2 for ERROR.
	 */
	private int changeUserInfo(UserProfile userProfile, String property, String newValue) {
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("UPDATE `users` SET `" + property + "` = ? WHERE `id` = ?   ")) {
			preparedStatement.setString(1, newValue);
			preparedStatement.setInt(2, userProfile.getId());

			preparedStatement.execute();
			return CHANGE_DONE;
		} catch (Exception exception) {
			return CHANGE_ERROR;
		}
	}

	/**
	 * @param userProfile
	 * @param changePacket
	 * @return An array of int like [0,1,1,1] -> usernameChanged, emailChanged, passwordChanged, imageUrlChanged
	 */
	private int[] changeUserInfo(UserProfile userProfile, UserSettingsChangePacket changePacket) {
		int usernameChanged = CHANGE_IGNORED, emailChanged = CHANGE_IGNORED, passwordChanged = CHANGE_IGNORED, imageUrlChanged = CHANGE_IGNORED;

		if (changePacket.getUsername() != null) {
			usernameChanged = this.changeUserInfo(userProfile, "username", changePacket.getUsername());
		}

		if (changePacket.getEmail() != null) {
			emailChanged = this.changeUserInfo(userProfile, "email", changePacket.getEmail());
		}

		if (changePacket.getPassword() != null) {
			passwordChanged = this.changeUserInfo(userProfile, "password", changePacket.getPassword());
		}

		if (changePacket.getImageUrl() != null) {
			imageUrlChanged = this.changeUserInfo(userProfile, "image_url", changePacket.getImageUrl());
		}

		return new int[]{usernameChanged, emailChanged, passwordChanged, imageUrlChanged};
	}

	/**
	 * Login user to database.
	 *
	 * @param loginPacket A login packet where are user field input.
	 * @return true if the user is logged or false if not.
	 */
	private boolean login(UserLoginPacket loginPacket) {
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT * FROM `users` WHERE `email` = ? AND `password` = ?;")) {
			preparedStatement.setString(1, loginPacket.getEmail());
			preparedStatement.setString(2, loginPacket.getPassword());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return false;
	}

	/**
	 * Register a new user into database.
	 *
	 * @param registerPacket
	 * @return An UserAuthentificationErrorPacket.ErrorType
	 */
	private UserAuthentificationErrorPacket.ErrorType register(UserRegisterPacket registerPacket) {
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT * FROM `users` WHERE `username` = ? OR `email` = ?;")) {
			preparedStatement.setString(1, registerPacket.getUsername());
			preparedStatement.setString(2, registerPacket.getEmail());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return UserAuthentificationErrorPacket.ErrorType.EMAIL_ALREADY_USED;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("INSERT INTO `users` (`username`, `email`, `password`, `enabled`, `role_name`, `image_url`) VALUES (?, ?, ?, 'Y', 'USER', ?);")) {
			preparedStatement.setString(1, registerPacket.getUsername());
			preparedStatement.setString(2, registerPacket.getEmail());
			preparedStatement.setString(3, registerPacket.getPassword());
			preparedStatement.setString(4, "https://imgur.com/download/ljrctP0"); //default image link TODO: change it

			preparedStatement.execute();

			return null;
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return UserAuthentificationErrorPacket.ErrorType.SERVER_ERROR;
	}

	/**
	 * Find username by column with value.
	 *
	 * @param column Database column
	 * @param value  Value to find.
	 * @return An UserProfile.
	 */
	public UserProfile findUserBy(String column, Object value) {
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT * FROM users WHERE " + column + " = ?;")) {
			preparedStatement.setObject(1, value);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					return null;
				}

				return this.createUserProfileFromResultSet(resultSet);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return null;
	}

	/**
	 * Get user by email in database.
	 *
	 * @param targetEmail
	 * @return An UserProfile
	 * @see UserManager#findUserBy(String, Object)
	 */
	public UserProfile findUserByEmail(String targetEmail) {
		return this.findUserBy("email", targetEmail);
	}

	/**
	 * Get user by id in database.
	 *
	 * @param targetId
	 * @return An UserProfile
	 * @see UserManager#findUserBy(String, Object)
	 */
	public UserProfile findUserById(int targetId) {
		return this.findUserBy("id", targetId);
	}

	/**
	 * Re construct result of query in database to UserProfile Object.
	 *
	 * @param resultSet result of Database query.
	 * @return UserProfile built.
	 * @throws SQLException
	 */
	public UserProfile createUserProfileFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String username = resultSet.getString("username");
		String email = resultSet.getString("email");
		String imageUrl = resultSet.getString("image_url");
		boolean enabled = resultSet.getString("enabled").charAt(0) == 'Y';
		boolean connected = true; // TODO
		boolean admin = "ADMIN".equalsIgnoreCase(resultSet.getString("role_name"));
		String creationDate = resultSet.getString("created_at");

		return new UserProfile(id, username, email, imageUrl, enabled, connected, admin, creationDate);
	}

	/**
	 * Get all friends of someone.
	 *
	 * @param ofUserProfile Userprofile to get friends.
	 * @return A list of UserProfile of friends.
	 */
	public List<UserProfile> findFriendsByUser(UserProfile ofUserProfile) {
		List<UserProfile> friends = new ArrayList<>();

		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("" +
				"SELECT DISTINCT\n" +
				"    user.*\n" +
				"FROM\n" +
				"    users user\n" +
				"INNER JOIN are_friends friendship \n" +
				"    ON friendship.id_user_1 = user.id OR friendship.id_user_2 = user.id\n" +
				"WHERE\n" +
				"    (friendship.id_user_1 = ?\n" +
				"\tOR friendship.id_user_2 = ?)\n" +
				"\tAND user.id != ?;")) {
			for (int i = 0; i < 3; i++) {
				preparedStatement.setObject(i + 1, ofUserProfile.getId());
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					friends.add(this.createUserProfileFromResultSet(resultSet));
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return friends;
	}

	/**
	 * Get the message between tow people.
	 *
	 * @param fromUserId     User id who send the message.
	 * @param toUserId User id of receiver
	 * @return A List of ExchangedMessage between 2 user.
	 */
	public List<ExchangedMessage> getExchangedMessageWith(int fromUserId, int toUserId) {
		List<ExchangedMessage> messages = new ArrayList<>();

		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT `sent`, `content`, `id_sender` FROM `messages` WHERE (`id_sender` = ? OR `id_sender` = ?) AND (`id_receiver` = ? OR `id_receiver` = ?) ORDER BY `id` DESC LIMIT 50 ;")) {
			preparedStatement.setInt(1, fromUserId);
			preparedStatement.setInt(2, toUserId);
			preparedStatement.setInt(3, fromUserId);
			preparedStatement.setInt(4, toUserId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Date date = resultSet.getDate("sent");
					String content = resultSet.getString("content");
					int senderId = resultSet.getInt("id_sender");

					messages.add(new ExchangedMessage(senderId, date, content));
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		Collections.reverse(messages);
		return messages;
	}

	public void addMessage(int senderId, ExchangedMessage message) {
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("INSERT INTO messages (sent, content, id_sender, id_receiver, opened) VALUES (?, ?, ?, ?, false);")) {
			preparedStatement.setDate(1, new java.sql.Date(message.getDateObject().getTime())); /* Conversion was mandatory to work... */
			preparedStatement.setString(2, message.getContent());
			preparedStatement.setInt(3, senderId);
			preparedStatement.setInt(4, message.getUserId());

			preparedStatement.execute();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}