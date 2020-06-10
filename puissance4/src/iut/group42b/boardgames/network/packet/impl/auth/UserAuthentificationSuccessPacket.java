package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;

public class UserAuthentificationSuccessPacket implements IPacket {

	/* Variables */
	private SuccessType successType;
	private UserProfile userProfile;

	/* Constructor */
	public UserAuthentificationSuccessPacket() {
		this(null, null);
	}

	/* Constructor */
	public UserAuthentificationSuccessPacket(SuccessType successType, UserProfile userProfile) {
		this.successType = successType;
		this.userProfile = userProfile;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) successType.ordinal());

		if (SuccessType.LOGGED.equals(successType)) {
			userProfile.write(buffer);
		}
	}

	@Override
	public void read(DataBuffer buffer) {
		successType = SuccessType.values()[buffer.readByte()];

		if (SuccessType.LOGGED.equals(successType)) {
			userProfile = new UserProfile();
			userProfile.read(buffer);
		}
	}

	public SuccessType getSuccessType() {
		return successType;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	@Override
	public String toString() {
		return "UserAuthentificationSuccessPacket{" +
				"successType=" + successType +
				", userProfile=" + userProfile +
				'}';
	}

	public enum SuccessType {

		REGISTER_COMPLETE,
		LOGGED

	}

}