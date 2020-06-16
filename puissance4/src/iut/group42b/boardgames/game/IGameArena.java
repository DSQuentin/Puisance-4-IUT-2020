package iut.group42b.boardgames.game;

import iut.group42b.boardgames.network.packet.IPacket;

public interface IGameArena {

	/**
	 * Start a game.
	 *
	 * @param gameHandler IGameHandler.
	 */
	void start(IGameHandler gameHandler);

	/**
	 * End a game.
	 *
	 * @param gameHandler IGameHandler.
	 */
	void end(IGameHandler gameHandler);

	/**
	 * Broadcast a packet.
	 *
	 * @param packet Packet to broadcast.
	 */
	void broadcast(IPacket packet);

	/**
	 * Get database ID.
	 *
	 * @return
	 */
	int getDatabaseId();

	/**
	 * Set database ID.
	 *
	 * @param id
	 */
	void setDatabaseId(int id);

	/**
	 * Get JSON GameState
	 *
	 * @return String
	 */
	String toJSONGameState();

}