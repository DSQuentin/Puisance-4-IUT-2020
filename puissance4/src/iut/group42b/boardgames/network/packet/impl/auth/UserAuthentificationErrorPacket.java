package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class UserAuthentificationErrorPacket implements IPacket {

	/* Variables */
	private ErrorType errorType;
	private String extra;

	/* Constructor */
	public UserAuthentificationErrorPacket() {
		this(null, null);
	}

	/* Constructor */
	public UserAuthentificationErrorPacket(ErrorType errorType) {
		this(errorType, null);
	}

	/* Constructor */
	public UserAuthentificationErrorPacket(ErrorType errorType, String extra) {
		this.errorType = errorType;
		this.extra = extra == null ? "" : extra;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) errorType.ordinal());
		buffer.write(extra);
	}

	@Override
	public void read(DataBuffer buffer) {
		errorType = ErrorType.values()[buffer.readByte()];
		extra = buffer.readString();
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public String getExtra() {
		return extra;
	}

	@Override
	public String toString() {
		return "UserAuthentificationErrorPacket{" + "errorType=" + errorType + ", extra='" + extra + '\'' + '}';
	}

	public enum ErrorType {

		EMAIL_ALREADY_USED,
		PASSWORD_TOO_WEAK,
		FIELD_EMPTY,
		BAD_EMAIL_OR_PASSWORD,
		SERVER_ERROR;

	}

}