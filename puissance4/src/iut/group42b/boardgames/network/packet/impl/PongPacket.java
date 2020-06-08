package iut.group42b.boardgames.network.packet.impl;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class PongPacket implements IPacket {

	/* Variables */
	private long millis;

	/* Constructor */
	public PongPacket() {
		this(0);
	}

	/* Constructor */
	public PongPacket(PingPacket pingPacket) {
		this(pingPacket.getMillis());
	}

	/* Constructor */
	public PongPacket(long millis) {
		this.millis = millis;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(millis);
	}

	@Override
	public void read(DataBuffer buffer) {
		millis = buffer.readLong();
	}

	public long getMillis() {
		return millis;
	}

}