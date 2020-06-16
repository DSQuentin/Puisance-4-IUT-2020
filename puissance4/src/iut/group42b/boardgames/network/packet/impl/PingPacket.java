package iut.group42b.boardgames.network.packet.impl;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class PingPacket implements IPacket {

	/* Variables */
	private final long millis;

	/**
	 * Constructor PingPacket
	 */
	public PingPacket() {
		this(System.currentTimeMillis());
	}

	/**
	 * Constructor PingPacket
	 *
	 * @param millis A duration in milliseconds.
	 */
	public PingPacket(long millis) {
		this.millis = millis;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.millis);
	}

	@Override
	public void read(DataBuffer buffer) {
	}

	/**
	 * Get a duration
	 *
	 * @return Duration in milliseconds.
	 */
	public long getMillis() {
		return this.millis;
	}

}