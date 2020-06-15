package iut.group42b.boardgames.game;

import iut.group42b.boardgames.network.packet.IPacket;

public interface IGameArena {

	public void start(IGameHandler gameHandler);

	public void end(IGameHandler gameHandler);

	public void broadcast(IPacket packet);

	public int getDatabaseId();

	public void setDatabaseId(int id);

	public String toJSONGameState();

}