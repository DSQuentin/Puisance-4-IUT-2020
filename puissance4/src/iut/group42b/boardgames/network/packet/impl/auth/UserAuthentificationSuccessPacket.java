package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;

public class UserAuthentificationSuccessPacket implements IPacket {

	/* Variables */
	private SuccessType successType;
	private UserProfile userProfile;

	/**
	 * Constructor UserAuthentificationSuccessPacket Empty to rebuild packet.
	 */
	public UserAuthentificationSuccessPacket() {
		this(null, null);
	}

	/**
	 * Constructor UserAuthentificationSuccessPacket
	 *
	 * @param successType SuccessType
	 * @param userProfile UserProfile
	 */
	public UserAuthentificationSuccessPacket(SuccessType successType, UserProfile userProfile) {
		this.successType = successType;
		this.userProfile = userProfile;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) this.successType.ordinal());

		if (SuccessType.LOGGED.equals(this.successType)) {
			this.userProfile.write(buffer);
		}
	}

	@Override
	public void read(DataBuffer buffer) {
		this.successType = SuccessType.values()[buffer.readByte()];

		if (SuccessType.LOGGED.equals(this.successType)) {
			this.userProfile = new UserProfile();
			this.userProfile.read(buffer);
		}
	}

	/**
	 * Get the success type.
	 *
	 * @return SuccessType
	 */
	public SuccessType getSuccessType() {
		return this.successType;
	}

	/**
	 * Get the user profile.
	 *
	 * @return UserProfile.
	 */
	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	@Override
	public String toString() {
		return "UserAuthentificationSuccessPacket{" +
				"successType=" + this.successType +
				", userProfile=" + this.userProfile +
				'}';
	}

	public enum SuccessType {

		REGISTER_COMPLETE,
		LOGGED

	}

}