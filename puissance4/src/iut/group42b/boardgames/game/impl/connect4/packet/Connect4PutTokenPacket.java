package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import iut.group42b.boardgames.util.DataBuffer;

public class Connect4PutTokenPacket extends Connect4BasePacket {

	/* Variables */
	private Connect4Side side;
	private int x;
	private int y;

	/* Constructor */
	public Connect4PutTokenPacket() {
		this(0, null, 0, 0);
	}

	/* Constructor */
	public Connect4PutTokenPacket(int arenaIdentifier, Connect4Side side, int x, int y) {
		super(arenaIdentifier);

		this.side = side;
		this.x = x;
		this.y = y;
	}

	@Override
	public void write(DataBuffer buffer) {
		super.write(buffer);

		buffer.write((byte) side.ordinal());
		buffer.write((byte) x);
		buffer.write((byte) y);
	}

	@Override
	public void read(DataBuffer buffer) {
		super.read(buffer);

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
}