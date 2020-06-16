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


	/**
	 * Constructor UserSearchResponsePacket Empty to rebuild the packet.
	 */
	public UserSearchResponsePacket() {
		this(null, Collections.emptyList());
	}

	/**
	 * Constructor UserSearchResponsePacket
	 *
	 * @param query String.
	 * @param found List<UserProfile>.
	 */
	public UserSearchResponsePacket(String query, List<UserProfile> found) {
		this.query = query;
		this.found = found;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.query);
		buffer.writeList(this.found);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.query = buffer.readString();
		this.found = buffer.readList(UserProfile::new);
	}

	/**
	 * Get the Query.
	 *
	 * @return String
	 */
	public String getQuery() {
		return this.query;
	}

	/**
	 * Get Founded User Profiles.
	 *
	 * @return List<UserProfile>
	 */
	public List<UserProfile> getFound() {
		return this.found;
	}

}