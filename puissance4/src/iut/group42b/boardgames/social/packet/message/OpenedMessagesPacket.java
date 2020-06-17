package iut.group42b.boardgames.social.packet.message;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class OpenedMessagesPacket implements IPacket {

	/* Variables */
	private int userId;

	/* Constructor */
	public OpenedMessagesPacket() {
		this(0);
	}

	/* Constructor */
	public OpenedMessagesPacket(int userId) {
		this.userId = userId;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.userId);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.userId = buffer.readInt();
	}

	public int getUserId() {
		return this.userId;
	}

}