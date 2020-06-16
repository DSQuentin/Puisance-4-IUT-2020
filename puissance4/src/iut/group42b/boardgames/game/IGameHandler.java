package iut.group42b.boardgames.game;

import iut.group42b.boardgames.game.player.Player;

import java.util.List;

public interface IGameHandler {

	/**
	 * Register Packets.
	 */
	void registerPackets();

	/**
	 * Create arena for players.
	 *
	 * @param players List of players.
	 * @return IGameArena
	 */
	IGameArena createArena(List<Player> players);

}