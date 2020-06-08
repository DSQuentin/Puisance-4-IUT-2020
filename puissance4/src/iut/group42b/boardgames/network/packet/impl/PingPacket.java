package iut.group42b.boardgames.network.packet.impl;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class PingPacket implements IPacket {

	/* Variables */
	private final long millis;

	/* Constructor */
	public PingPacket() {
		this(System.currentTimeMillis());
	}

	/* Constructor */
	public PingPacket(long millis) {
		this.millis = millis;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(millis);
	}

	@Override
	public void read(DataBuffer buffer) {
		;
	}

	public long getMillis() {
		return millis;
	}

}