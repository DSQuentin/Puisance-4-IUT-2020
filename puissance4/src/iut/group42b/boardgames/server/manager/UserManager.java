package iut.group42b.boardgames.server.manager;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.impl.auth.*;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;
import iut.group42b.boardgames.social.packet.friendship.FriendNotFoundPacket;
import iut.group42b.boardgames.social.packet.friendship.FriendNumberPacket;
import iut.group42b.boardgames.social.packet.friendship.FriendNumberRequestPacket;
import iut.group42b.boardgames.social.packet.friendship.FriendRequestPacket;
import iut.group42b.boardgames.social.packet.history.GameListHistoryPacket;
import iut.group42b.boardgames.util.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
		} else if (packet instanceof GameListHistoryPacket) {
			GameListHistoryPacket historyPacket = (GameListHistoryPacket) packet;

			int userId = historyPacket.getUserId();
			List<GameHistoryItem> history = this.getGameHistory(userId);

			handler.queue(new GameListHistoryPacket(userId, history.size(), history.subList(0, Math.min(history.size(), 10))));


		}else if (packet instanceof FriendNumberRequestPacket) {
			FriendNumberRequestPacket friendNumberPacket = (FriendNumberRequestPacket) packet;

			int numberOfFriends = friendNumberPacket.getQuery();

			handler.queue(new FriendNumberPacket(this.getNumberOfFriendsById(numberOfFriends)));
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
			preparedStatement.setString(4, "https://api.adorable.io/avatars/174/" + registerPacket.getEmail() + ".png");

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
	 * Get user by username in database.
	 *
	 * @param targetName
	 * @return An UserProfile
	 * @see UserManager#findUserBy(String, Object)
	 */
	public UserProfile findUserByUsername(String targetName) {
		return this.findUserBy("username", targetName);
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
		boolean connected = true;
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
				"SELECT DISTINCT " +
				"    user.* " +
				"FROM " +
				"    users user " +
				"INNER JOIN are_friends friendship  " +
				"    ON friendship.id_user_1 = user.id OR friendship.id_user_2 = user.id " +
				"WHERE " +
				"    (friendship.id_user_1 = ? " +
				"    OR friendship.id_user_2 = ?) " +
				"    AND user.id != ?;")) {
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

	public List<GameHistoryItem> getGameHistory(int userId) {
		List<GameHistoryItem> gameHistory = new ArrayList<>();

		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("" +
				"SELECT " +
				"    `state_number`, " +
				"    `user1`.`id`                                         AS `user1_id`, " +
				"    `user1`.`username`                                   AS `user1_name`, " +
				"    `user2`.`id`                                         AS `user2_id`, " +
				"    `user2`.`username`                                   AS `user2_name`, " +
				"    `id_user_winner`, " +
				"    TIMEDIFF(`end_at`, `started_at`)                            AS `duration`, " +
				"    IF(`id_user_winner` = `id_user_1`, `score_1`, `score_2`)    AS `winner_score`, " +
				"    `started_at` " +
				"FROM `played_games` " +
				"INNER JOIN `users` `user1` " +
				"    ON `user1`.`id` = `played_games`.`id_user_1` " +
				"INNER JOIN `users` `user2` " +
				"    ON `user2`.`id` = `played_games`.`id_user_2` " +
				"WHERE " +
				"    (`played_games`.`id_user_1` = ? " +
				"    OR `played_games`.`id_user_2` = ?) " +
				"    AND `played_games`.`end_at` IS NOT NULL " +
				"ORDER BY `played_games`.`id` DESC ;")) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, userId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					if (gameHistory.size() < 10) { /* Avoid loading too much into memory. */
						int gameState = resultSet.getInt("state_number");
						int user1Id = resultSet.getInt("user1_id");
						String user1Name = resultSet.getString("user1_name");
						int user2Id = resultSet.getInt("user2_id");
						String user2Name = resultSet.getString("user2_name");
						int idUserWinner = resultSet.getInt("id_user_winner");
						String duration = resultSet.getString("duration");
						int winnerScore = resultSet.getInt("winner_score");
						String startedAt = resultSet.getString("started_at");

						gameHistory.add(new GameHistoryItem(gameState, user1Id, user1Name, user2Id, user2Name, idUserWinner, duration, winnerScore, startedAt));

					} else {
						gameHistory.add(null); /* But still 'counting' them. */
					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return gameHistory;
	}

	public int getNumberOfFriendsById(int userId) {
		int numberOfFriends = 0;
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT count(*) FROM `are_friends` WHERE `id_user_1`= ? OR `id_user_2` = ? ;")) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, userId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					 numberOfFriends = resultSet.getInt("count(*)");
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return numberOfFriends;
	}


}