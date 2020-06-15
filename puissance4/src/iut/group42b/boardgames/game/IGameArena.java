package iut.group42b.boardgames.game;

import iut.group42b.boardgames.network.packet.IPacket;

public interface IGameArena {

	void start(IGameHandler gameHandler);

	void end(IGameHandler gameHandler);

	void broadcast(IPacket packet);

	int getDatabaseId();

	void setDatabaseId(int id);

	String toJSONGameState();

}