package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class UserLoginPacket implements IPacket {

	/* Variables */
	private String email, password;

	/**
	 * Constructor UserLoginPacket Empty to rebuild the packet.
	 */
	public UserLoginPacket() {
		this(null, null);
	}

	/**
	 * Constructor UserLoginPacket.
	 *
	 * @param email    String.
	 * @param password String.
	 */
	public UserLoginPacket(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.email);
		buffer.write(this.password);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.email = buffer.readString();
		this.password = buffer.readString();
	}

	/**
	 * Get the Email.
	 *
	 * @return String.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Get the Password
	 *
	 * @return String.
	 */
	public String getPassword() {
		return this.password;
	}

}