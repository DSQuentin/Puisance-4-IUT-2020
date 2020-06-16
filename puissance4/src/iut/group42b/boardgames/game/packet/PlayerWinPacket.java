package iut.group42b.boardgames.game.packet;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class PlayerWinPacket implements IPacket {

	/* Constants */
	public static final int MODE_NORMAL = 0;
	public static final int MODE_SURRENDER = 1;
	public static final int MODE_CONNECTION_LOST = 2;

	/* Variables */
	private int mode;

	/* Constructor */
	public PlayerWinPacket() {
		this(MODE_NORMAL);
	}

	/* Constructor */
	public PlayerWinPacket(int mode) {
		this.mode = mode;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) this.mode);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.mode = buffer.readByte();
	}

	public int getMode() {
		return this.mode;
	}

	public boolean isNormal() {
		return this.mode == MODE_NORMAL;
	}

	public boolean isSurrender() {
		return this.mode == MODE_SURRENDER;
	}

	public boolean isConnectionLost() {
		return this.mode == MODE_CONNECTION_LOST;
	}

}