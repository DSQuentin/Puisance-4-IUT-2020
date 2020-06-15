package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class Connect4PutTokenPacket implements IConnect4Packet {

	/* Variables */
	private Connect4Side side;
	private int x;
	private int y;

	/* Constructor */
	public Connect4PutTokenPacket() {
		this(null, 0, 0);
	}

	/* Constructor */
	public Connect4PutTokenPacket(Connect4Side side, int x, int y) {
		this.side = side;
		this.x = x;
		this.y = y;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) side.ordinal());
		buffer.write((byte) x);
		buffer.write((byte) y);
	}

	@Override
	public void read(DataBuffer buffer) {
		side = Connect4Side.values()[buffer.readByte()];
		x = buffer.readByte();
		y = buffer.readByte();
	}

	public Connect4Side getSide() {
		return side;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Connect4PutTokenPacket{" +
				"side=" + side +
				", x=" + x +
				", y=" + y +
				'}';
	}
}