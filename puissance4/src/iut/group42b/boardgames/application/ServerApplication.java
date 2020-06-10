package iut.group42b.boardgames.application;

import iut.group42b.boardgames.Bootstrap;
import iut.group42b.boardgames.server.database.impl.MariaConnection;
import iut.group42b.boardgames.server.manager.DatabaseInterface;
import iut.group42b.boardgames.server.network.NetworkServer;
import iut.group42b.boardgames.util.Logger;

public class ServerApplication {

	/* Logger */
	private final static Logger LOGGER = new Logger(ServerApplication.class);

	public static void startFromBootstrap() throws Exception {
		LOGGER.verbose("Starting server...");

		/*AbstractDatabaseConnection connection = new MariaConnection("localhost", "iut", "root", "password"); */

        /*try (Statement statement = connection.createStatement()) {
            // "SELECT * FROM users WHERE email = '" + packet.getEmail() +"';"
        }*/
        /*try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?;")) {
            preparedStatement.setString(1, "hello@world'\\@;''");
            preparedStatement.setString(2, "hello@world'\\@;''");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                }
            }
        }*/

		DatabaseInterface.get().initialize(new MariaConnection("46.105.92.223", "db42b", "groupe42b", "20@info!iuto42b"));

		NetworkServer server = new NetworkServer((int) Bootstrap.PORT_OPTION.getValue());
		server.listen();
	}

}