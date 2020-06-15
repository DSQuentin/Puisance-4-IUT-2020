package iut.group42b.boardgames.game;

import iut.group42b.boardgames.game.player.Player;

import java.util.List;

public interface IGameHandler {

	void registerPackets();

	IGameArena createArena(List<Player> players);

}