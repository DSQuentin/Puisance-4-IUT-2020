package iut.group42b.boardgames.network.packet.impl.connection;

import iut.group42b.boardgames.network.packet.IEmptyPacket;

public class HeartbeatPacket implements IEmptyPacket {

	/**
	 * Singleton used to avoid creating multiple instance
	 */
	public static final HeartbeatPacket INSTANCE = new HeartbeatPacket();

}