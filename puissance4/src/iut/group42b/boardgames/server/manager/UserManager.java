package iut.group42b.boardgames.server.manager;

import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.impl.auth.*;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.function.BiFunction;

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
		;
	}

	public static UserManager get() {
		return INSTANCE;
	}

	// SELECT * FROM users WHERE email = ? AND password = ?
	// if result != 0
	//    success
	// else
	//    fail

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		if (packet instanceof UserLoginPacket) {
			UserLoginPacket loginPacket = (UserLoginPacket) packet;

			if (login(loginPacket)) {
				UserProfile userProfile = findUserByEmail(loginPacket.getEmail());

				handler.setProfile(userProfile);
				handler.queue(new UserAuthentificationSuccessPacket(UserAuthentificationSuccessPacket.SuccessType.LOGGED, userProfile));
			} else {
				handler.setProfile(null);
				handler.queue(new UserAuthentificationErrorPacket(UserAuthentificationErrorPacket.ErrorType.BAD_EMAIL_OR_PASSWORD, loginPacket.getEmail()));
			}
		} else if (packet instanceof UserRegisterPacket) {
			UserRegisterPacket registerPacket = (UserRegisterPacket) packet;

			handler.setProfile(null);

			UserAuthentificationErrorPacket.ErrorType error = register(registerPacket);

			if (error == null) {
				handler.queue(new UserAuthentificationSuccessPacket(UserAuthentificationSuccessPacket.SuccessType.REGISTER_COMPLETE, null));
			} else {
				handler.queue(new UserAuthentificationErrorPacket(error));
			}
		} else if (packet instanceof UserSettingsChangePacket) {
			UserSettingsChangePacket changePacket = (UserSettingsChangePacket) packet;

			int[] changed = changeUserInfo(handler.getUserProfile(), changePacket);
			UserProfile newUserProfile = findUserById(handler.getUserProfile().getId());

			handler.setProfile(newUserProfile);

			handler.queue(new UserSettingsChangedPacket(changed, newUserProfile));
		}
	}

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

	private int[] changeUserInfo(UserProfile userProfile, UserSettingsChangePacket changePacket) {
		int usernameChanged = CHANGE_IGNORED, emailChanged = CHANGE_IGNORED, passwordChanged = CHANGE_IGNORED, imageUrlChanged = CHANGE_IGNORED;

		if (changePacket.getUsername() != null) {
			usernameChanged = changeUserInfo(userProfile, "username", changePacket.getUsername());
		}

		if (changePacket.getEmail() != null) {
			emailChanged = changeUserInfo(userProfile, "email", changePacket.getEmail());
		}

		if (changePacket.getPassword() != null) {
			passwordChanged = changeUserInfo(userProfile, "password", changePacket.getPassword());
		}

		if (changePacket.getImageUrl() != null) {
			imageUrlChanged = changeUserInfo(userProfile, "image_url", changePacket.getImageUrl());
		}

		return new int[] { usernameChanged, emailChanged, passwordChanged, imageUrlChanged };
	}

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
			preparedStatement.setString(4, "https://imgur.com/download/ljrctP0"); //default image link

			preparedStatement.execute();

			return null;
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return UserAuthentificationErrorPacket.ErrorType.SERVER_ERROR;
	}

	public UserProfile findUserBy(String column, Object value) {
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT * FROM users WHERE " + column + " = ?;")) {
			preparedStatement.setObject(1, value);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					return null;
				}

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
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return null;
	}

	public UserProfile findUserByEmail(String targetEmail) {
		return findUserBy("email", targetEmail);
	}

	public UserProfile findUserById(int targetId) {
		return findUserBy("id", targetId);
	}

}