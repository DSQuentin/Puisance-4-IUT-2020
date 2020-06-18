package iut.group42b.boardgames.social.packet.friendship;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class FriendNumberRequestPacket implements IPacket {


	/* Variables */
	private int query;

	/**
	 * FriendNumberRequestPacket Constructor Empty to rebuild the packet.
	 */
	public FriendNumberRequestPacket() {
		this(0);
	}


	/**
	 * Constructor FriendNumberRequestPacket.
	 *
	 * @param userId
	 */
	public FriendNumberRequestPacket(int userId) {
		this.query = userId;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.query);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.query = buffer.readInt();
	}

	/**
	 * Get the query.
	 *
	 * @return Int.
	 */
	public int getQuery() {
		return this.query;
	}

}