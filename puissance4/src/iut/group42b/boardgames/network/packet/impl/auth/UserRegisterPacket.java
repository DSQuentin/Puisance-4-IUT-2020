package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class UserRegisterPacket implements IPacket {

	/* Variables */
	private String username, email, password;

	/* Constructor */
	public UserRegisterPacket() {
		this(null, null, null);
	}

	/* Constructor */
	public UserRegisterPacket(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(username);
		buffer.write(email);
		buffer.write(password);
	}

	@Override
	public void read(DataBuffer buffer) {
		username = buffer.readString();
		email = buffer.readString();
		password = buffer.readString();
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}