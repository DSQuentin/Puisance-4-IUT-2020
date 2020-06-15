package iut.group42b.boardgames.game.player;

import iut.group42b.boardgames.network.SocketHandler;

public class Player {

	/* Variables */
	private final SocketHandler socketHandler;

	public Player(SocketHandler socketHandler) {
		this.socketHandler = socketHandler;
	}

	public SocketHandler getSocketHandler() {
		return socketHandler;
	}

}