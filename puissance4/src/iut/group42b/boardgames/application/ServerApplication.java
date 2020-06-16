package iut.group42b.boardgames.application;

import iut.group42b.boardgames.Bootstrap;
import iut.group42b.boardgames.server.database.impl.MariaConnection;
import iut.group42b.boardgames.server.manager.DatabaseInterface;
import iut.group42b.boardgames.server.network.NetworkServer;
import iut.group42b.boardgames.util.Logger;

public class ServerApplication {

	/* Logger */
	private final static Logger LOGGER = new Logger(ServerApplication.class);

	/* Static */
	private static NetworkServer server;

	public static void startFromBootstrap() throws Exception {
		LOGGER.verbose("Starting server...");

		// TODO Expose it in a config file or CLI
		DatabaseInterface.get().initialize(new MariaConnection("46.105.92.223", "db42b", "groupe42b", "20@info!iuto42b"));

		server = new NetworkServer(Integer.parseInt(Bootstrap.PORT_OPTION.getValue().toString()));
		server.listen();
	}

	public static NetworkServer getServer() {
		return server;
	}
}