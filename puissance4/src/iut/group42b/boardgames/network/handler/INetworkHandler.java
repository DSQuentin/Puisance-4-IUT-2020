package iut.group42b.boardgames.network.handler;

import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.packet.IPacket;

public interface INetworkHandler {

	void handlePacket(SocketHandler handler, IPacket packet);

}