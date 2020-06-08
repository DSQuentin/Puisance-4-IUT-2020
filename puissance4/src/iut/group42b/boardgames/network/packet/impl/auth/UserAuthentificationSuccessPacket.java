package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class UserAuthentificationSuccessPacket implements IPacket {

	/* Variables */
	private SuccessType successType;

	/* Constructor */
	public UserAuthentificationSuccessPacket() {
		this(null);
	}

	/* Constructor */
	public UserAuthentificationSuccessPacket(SuccessType successType) {
		this.successType = successType;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.writeByte((byte) successType.ordinal());
	}

	@Override
	public void read(DataBuffer buffer) {
		successType = SuccessType.values()[buffer.readByte()];
	}

	public SuccessType getSuccessType() {
		return successType;
	}

	@Override
	public String toString() {
		return "UserAuthentificationSuccessPacket{" + "successType=" + successType + '}';
	}

	public enum SuccessType {

		REGISTER_COMPLETE,
		LOGGED

	}

}