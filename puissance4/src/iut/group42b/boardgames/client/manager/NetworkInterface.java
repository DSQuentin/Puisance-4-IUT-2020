package iut.group42b.boardgames.client.manager;

import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.util.Logger;

public class NetworkInterface {

	/* Logger */
	private final static Logger LOGGER = new Logger(NetworkInterface.class);

	/* Singleton */
	// Allow only one Instance of NetworkInterface.
	private final static NetworkInterface INSTANCE = new NetworkInterface(); // http://patatos.over-blog.com/article-comment-creer-un-singleton-en-java-88774140.html

	/* Variables */
	private SocketHandler socketHandler;

	/**
	 * Constructor NetworkInterface
	 */
	private NetworkInterface() {
	}

	/**
	 * Attach SocketHandler
	 *
	 * @param socketHandler Socket handler.
	 * @throws Exception
	 */
	public void initialize(SocketHandler socketHandler) throws Exception {
		this.socketHandler = socketHandler;
	}

	/**
	 * Get the SocketHandler.
	 *
	 * @return SocketHandler.
	 */
	public SocketHandler getSocketHandler() {
		return this.socketHandler;
	}

	/**
	 * Get NetworkInterface
	 *
	 * @return NetworkInterface.
	 */
	public static NetworkInterface get() {
		return INSTANCE;
	}

}