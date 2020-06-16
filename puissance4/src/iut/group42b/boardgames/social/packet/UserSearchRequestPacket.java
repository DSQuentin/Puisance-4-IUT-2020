package iut.group42b.boardgames.social.packet;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class UserSearchRequestPacket implements IPacket {

	/* Variables */
	private String query;

	/**
	 * UserSearchRequestPacket Constructor Empty to rebuild the packet.
	 */
	public UserSearchRequestPacket() {
		this(null);
	}


	/**
	 * Constructor UserSearchRequestPacket.
	 *
	 * @param query
	 */
	public UserSearchRequestPacket(String query) {
		this.query = query;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.query);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.query = buffer.readString();
	}

	/**
	 * Get the query.
	 *
	 * @return String.
	 */
	public String getQuery() {
		return this.query;
	}

}