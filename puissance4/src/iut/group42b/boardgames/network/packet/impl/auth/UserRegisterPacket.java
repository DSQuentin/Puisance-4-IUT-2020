package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class UserRegisterPacket implements IPacket {

	/* Variables */
	private String username, email, password;

	/**
	 * Constructor UserRegisterPacket Empty to rebuild the packet.
	 */
	public UserRegisterPacket() {
		this(null, null, null);
	}

	/**
	 * Constructor UserRegisterPacket
	 *
	 * @param username String
	 * @param email    String
	 * @param password String
	 */
	public UserRegisterPacket(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.username);
		buffer.write(this.email);
		buffer.write(this.password);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.username = buffer.readString();
		this.email = buffer.readString();
		this.password = buffer.readString();
	}

	/**
	 * Get the Username.
	 *
	 * @return String
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Get the Email.
	 *
	 * @return String
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Get the Password.
	 *
	 * @return String
	 */
	public String getPassword() {
		return this.password;
	}
}