package iut.group42b.boardgames.social.packet;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;

import java.util.Collections;
import java.util.List;

public class UserSearchResponsePacket implements IPacket {

	/* Variables */
	private String query;
	private List<UserProfile> found;

	/* Constructor */
	public UserSearchResponsePacket() {
		this(null, Collections.emptyList());
	}

	/* Constructor */
	public UserSearchResponsePacket(String query, List<UserProfile> found) {
		this.query = query;
		this.found = found;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(query);
		buffer.writeList(found);
	}

	@Override
	public void read(DataBuffer buffer) {
		query = buffer.readString();
		found = buffer.readList(() -> new UserProfile());
	}

	public String getQuery() {
		return query;
	}

	public List<UserProfile> getFound() {
		return found;
	}

}