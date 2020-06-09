package iut.group42b.boardgames.social.packet;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class UserSearchRequestPacket implements IPacket {

	/* Variables */
	private String query;

	/* Constructor */
	public UserSearchRequestPacket() {
		this(null);
	}

	/* Constructor */
	public UserSearchRequestPacket(String query) {
		this.query = query;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(query);
	}

	@Override
	public void read(DataBuffer buffer) {
		query = buffer.readString();
	}

	public String getQuery() {
		return query;
	}

}