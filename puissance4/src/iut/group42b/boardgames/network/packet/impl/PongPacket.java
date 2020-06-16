package iut.group42b.boardgames.network.packet.impl;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class PongPacket implements IPacket {

	/* Variables */
	private long millis;

	/**
	 * Constructor PongPacket Empty to re build the packet when reading.
	 */
	public PongPacket() {
		this(0);
	}

	/**
	 * Constructor PongPacket
	 *
	 * @param pingPacket PingPacket.
	 */
	public PongPacket(PingPacket pingPacket) {
		this(pingPacket.getMillis());
	}

	/**
	 * Constructor PongPacket
	 *
	 * @param millis Long value.
	 */
	public PongPacket(long millis) {
		this.millis = millis;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.millis);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.millis = buffer.readLong();
	}

	/**
	 * Get duration int milliseconds.
	 *
	 * @return
	 */
	public long getMillis() {
		return this.millis;
	}

}