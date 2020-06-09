package iut.group42b.boardgames.client.manager;

import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.util.Logger;

public class NetworkInterface {

	/* Logger */
	private final static Logger LOGGER = new Logger(NetworkInterface.class);

	/* Singleton */
	private final static NetworkInterface INSTANCE = new NetworkInterface();

	/* Variables */
	private SocketHandler socketHandler;

	/* Constructor */
	private NetworkInterface() {
		;
	}

	public void initialize(SocketHandler socketHandler) throws Exception {
		this.socketHandler = socketHandler;
	}

	public SocketHandler getSocketHandler() {
		return socketHandler;
	}

	public static NetworkInterface get() {
		return INSTANCE;
	}

}