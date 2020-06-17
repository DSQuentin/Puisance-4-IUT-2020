package iut.group42b.boardgames.social.packet.friendship;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class FriendRequestPacket implements IPacket {

	/* Variables */
	private String name;

	/* Constructor */
	public FriendRequestPacket() {
		this("");
	}

	/* Constructor */
	public FriendRequestPacket(String name) {
		this.name = name;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(name);
	}

	@Override
	public void read(DataBuffer buffer) {
		name = buffer.readString();
	}

	public String getName() {
		return this.name;
	}

}