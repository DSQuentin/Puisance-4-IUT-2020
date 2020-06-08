package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class UserLoginPacket implements IPacket {

	/* Variables */
	private String email, password;

	/* Constructor */
	public UserLoginPacket() {
		this(null, null);
	}

	/* Constructor */
	public UserLoginPacket(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(email);
		buffer.write(password);
	}

	@Override
	public void read(DataBuffer buffer) {
		email = buffer.readString();
		password = buffer.readString();
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}