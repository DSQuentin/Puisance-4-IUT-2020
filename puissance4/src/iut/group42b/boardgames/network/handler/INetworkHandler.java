package iut.group42b.boardgames.network.handler;

import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.packet.IPacket;

public interface INetworkHandler {

	/**
	 * Handler for packet
	 *
	 * @param handler SocketHandler listening packet receive
	 * @param packet  A Packet
	 */
	void handlePacket(SocketHandler handler, IPacket packet);

}