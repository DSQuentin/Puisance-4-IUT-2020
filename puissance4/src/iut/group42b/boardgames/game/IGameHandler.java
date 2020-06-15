package iut.group42b.boardgames.game;

import iut.group42b.boardgames.game.player.Player;
import iut.group42b.boardgames.network.SocketHandler;

import java.util.List;

public interface IGameHandler {

	public void registerPackets();

	public IGameArena createArena(List<Player> players);

}