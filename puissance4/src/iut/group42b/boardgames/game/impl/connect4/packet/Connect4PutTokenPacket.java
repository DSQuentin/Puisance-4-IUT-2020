package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import iut.group42b.boardgames.util.DataBuffer;

public class Connect4PutTokenPacket implements IConnect4Packet {

	/* Variables */
	private Connect4Side side;
	private int x;
	private int y;


	/**
	 * Constructor Connect4PutTokenPacket Empty to rebuild
	 */
	public Connect4PutTokenPacket() {
		this(null, 0, 0);
	}


	/**
	 * Constructor Connect4PutTokenPacket
	 *
	 * @param side
	 * @param x
	 * @param y
	 */
	public Connect4PutTokenPacket(Connect4Side side, int x, int y) {
		this.side = side;
		this.x = x;
		this.y = y;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) this.side.ordinal());
		buffer.write((byte) this.x);
		buffer.write((byte) this.y);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.side = Connect4Side.values()[buffer.readByte()];
		this.x = buffer.readByte();
		this.y = buffer.readByte();
	}

	/**
	 * Get side
	 *
	 * @return
	 */
	public Connect4Side getSide() {
		return this.side;
	}

	/**
	 * Get X
	 *
	 * @return
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Get Y
	 *
	 * @return
	 */
	public int getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "Connect4PutTokenPacket{" +
				"side=" + this.side +
				", x=" + this.x +
				", y=" + this.y +
				'}';
	}
}