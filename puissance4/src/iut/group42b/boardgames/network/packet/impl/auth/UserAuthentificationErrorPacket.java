package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class UserAuthentificationErrorPacket implements IPacket {

	/* Variables */
	private ErrorType errorType;
	private String extra;

	/**
	 * Constructor UserAuthentificationErrorPacket Empty for rebuild the packet.
	 */
	public UserAuthentificationErrorPacket() {
		this(null, null);
	}

	/**
	 * Constructor UserAuthentificationErrorPacket.
	 *
	 * @param errorType ErrorType
	 */
	public UserAuthentificationErrorPacket(ErrorType errorType) {
		this(errorType, null);
	}

	/**
	 * Constructor UserAuthentificationErrorPacket
	 *
	 * @param errorType ErrorType
	 * @param extra     String
	 */
	public UserAuthentificationErrorPacket(ErrorType errorType, String extra) {
		this.errorType = errorType;
		this.extra = extra == null ? "" : extra;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) this.errorType.ordinal());
		buffer.write(this.extra);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.errorType = ErrorType.values()[buffer.readByte()];
		this.extra = buffer.readString();
	}

	/**
	 * Get error type.
	 *
	 * @return ErrorType.
	 */
	public ErrorType getErrorType() {
		return this.errorType;
	}

	/**
	 * Get Extra Errors
	 *
	 * @return String.
	 */
	public String getExtra() {
		return this.extra;
	}

	@Override
	public String toString() {
		return "UserAuthentificationErrorPacket{" + "errorType=" + this.errorType + ", extra='" + this.extra + '\'' + '}';
	}

	public enum ErrorType {

		EMAIL_ALREADY_USED,
		PASSWORD_TOO_WEAK,
		FIELD_EMPTY,
		BAD_EMAIL_OR_PASSWORD,
		SERVER_ERROR

	}

}