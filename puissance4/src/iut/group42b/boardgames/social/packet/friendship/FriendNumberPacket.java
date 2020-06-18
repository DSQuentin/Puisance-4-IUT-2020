package iut.group42b.boardgames.social.packet.friendship;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class FriendNumberPacket implements IPacket {

	private int number;

	/**
	 * Constructor FriendNumberPacket Empty to rebuild.
	 */
	public FriendNumberPacket() {
		this(0);
	}

	/**
	 * Constructor FriendNumberPacket
	 *
	 * @param number
	 */
	public FriendNumberPacket(int number) {
		this.number = number;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.number);

	}

	@Override
	public void read(DataBuffer buffer) {
		this.number = buffer.readInt();
	}

	/**
	 * Get number
	 *
	 * @return
	 */
	public int getNumber() {
		return this.number;
	}
}
