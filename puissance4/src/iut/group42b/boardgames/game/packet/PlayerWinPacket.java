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


	/**
	 * Constructor PlayerWinPacket Empty to rebuild
	 */
	public PlayerWinPacket() {
		this(MODE_NORMAL);
	}


	/**
	 * Constructor PlayerWinPacket.
	 *
	 * @param mode
	 */
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

	/**
	 * Get the mode.
	 *
	 * @return
	 */
	public int getMode() {
		return this.mode;
	}

	/**
	 * Check if game is normal
	 *
	 * @return
	 */
	public boolean isNormal() {
		return this.mode == MODE_NORMAL;
	}

	/**
	 * Check if game is surrended
	 *
	 * @return
	 */
	public boolean isSurrender() {
		return this.mode == MODE_SURRENDER;
	}

	/**
	 * Check if connection is lost s
	 *
	 * @return
	 */
	public boolean isConnectionLost() {
		return this.mode == MODE_CONNECTION_LOST;
	}

}