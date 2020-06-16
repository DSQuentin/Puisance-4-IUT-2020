package iut.group42b.boardgames.social.packet.friendship;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;

import java.util.Collections;
import java.util.List;

public class FriendListPacket implements IPacket {

	/* Variables */
	private List<UserProfile> users;

	/* Constructor */

	/**
	 * Constructor FriendListPacket Empty to rebuild the Packet.
	 */
	public FriendListPacket() {
		this(Collections.emptyList());
	}


	/**
	 * Constructor FriendListPacket.
	 *
	 * @param users List<UserProfile>.
	 */
	public FriendListPacket(List<UserProfile> users) {
		this.users = users;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.users);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.users = buffer.readList(UserProfile::new);
	}

	/**
	 * Get all Users.
	 *
	 * @return List<UserProfile>.
	 */
	public List<UserProfile> getUsers() {
		return this.users;
	}

}