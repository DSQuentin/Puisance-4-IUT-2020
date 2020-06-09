package iut.group42b.boardgames.server.manager;

import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationErrorPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationSuccessPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserLoginPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserRegisterPacket;
import iut.group42b.boardgames.util.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserManager implements INetworkHandler {

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
				handler.queue(new UserAuthentificationSuccessPacket(UserAuthentificationSuccessPacket.SuccessType.LOGGED));
			} else {
				handler.queue(new UserAuthentificationErrorPacket(UserAuthentificationErrorPacket.ErrorType.BAD_EMAIL_OR_PASSWORD, loginPacket.getEmail()));
			}
		} else if (packet instanceof UserRegisterPacket) {
			UserRegisterPacket registerPacket = (UserRegisterPacket) packet;

			UserAuthentificationErrorPacket.ErrorType error = register(registerPacket);

			if (error == null) {
				handler.queue(new UserAuthentificationSuccessPacket(UserAuthentificationSuccessPacket.SuccessType.REGISTER_COMPLETE));
			} else {
				handler.queue(new UserAuthentificationErrorPacket(error));
			}
		}
	}

	private boolean login(UserLoginPacket loginPacket) {
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?;")) {
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
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT * FROM users WHERE username = ? OR email = ?;")) {
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

		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("INSERT INTO users(username, email, password, enabled, role_name, image_url) VALUES (?, ?, ?, 'Y', 'USER', ?);")) {
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

}