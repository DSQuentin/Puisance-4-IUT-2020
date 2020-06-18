package iut.group42b.boardgames.server.network;


import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.server.manager.GameManager;
import iut.group42b.boardgames.server.manager.MatchmakingManager;
import iut.group42b.boardgames.server.manager.MessageManager;
import iut.group42b.boardgames.server.manager.UserManager;
import iut.group42b.boardgames.social.model.UserProfile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class NetworkServer {

	private final ServerSocket serverSocket;
	private final Set<SocketHandler> connectedHandlers;

	/**
	 * Constructor NetworkServer, init socket server with server port.
	 *
	 * @param port Socket port.
	 * @throws IOException
	 */
	public NetworkServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.connectedHandlers = new HashSet<>();
	}

	/**
	 * Listen connection form socket
	 */
	public void listen() {
		while (!this.serverSocket.isClosed()) {
			try {
				Socket socket = this.serverSocket.accept();
				SocketHandler socketHandler = new SocketHandler(socket);

				socketHandler.subscribe(UserManager.get());
				socketHandler.subscribe(GameManager.get());
				socketHandler.subscribe(MatchmakingManager.get());
				socketHandler.subscribe(MessageManager.get());
				socketHandler.newThread(); // create new thread for new client

				this.register(socketHandler);

			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public boolean register(SocketHandler socketHandler) {
		if (!socketHandler.isConnected()) {
			return false;
		}

		synchronized (this.connectedHandlers) {
			return this.connectedHandlers.add(socketHandler);
		}
	}

	public boolean unregister(SocketHandler socketHandler) {
		if (socketHandler.isConnected()) {
			return false;
		}

		synchronized (this.connectedHandlers) {
			return this.connectedHandlers.remove(socketHandler);
		}
	}

	public synchronized SocketHandler findSocketHandlerByProfileId(int userId) {
		synchronized (this.connectedHandlers) {
			for (SocketHandler socketHandler : this.connectedHandlers) {
				UserProfile userProfile = socketHandler.getUserProfile();

				if (userProfile != null && userProfile.getId() == userId) {
					return socketHandler;
				}
			}
		}
		return null;
	}

}
