package iut.group42b.boardgames.game.player;

import iut.group42b.boardgames.network.SocketHandler;

public class Player {

	/* Variables */
	private final SocketHandler socketHandler;

	/**
	 * Constructor Player
	 *
	 * @param socketHandler SocketHandler
	 */
	public Player(SocketHandler socketHandler) {
		this.socketHandler = socketHandler;
	}

	/**
	 * Get the socket handler.
	 *
	 * @return SocketHandler.
	 */
	public SocketHandler getSocketHandler() {
		return this.socketHandler;
	}

}