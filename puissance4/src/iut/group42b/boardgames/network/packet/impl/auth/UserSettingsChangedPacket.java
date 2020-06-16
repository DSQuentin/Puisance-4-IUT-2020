package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.server.manager.UserManager;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;

public class UserSettingsChangedPacket implements IPacket {

	/* Variables */
	private int usernameChanged;
	private int emailChanged;
	private int passwordChanged;
	private int imageUrlChange;
	private final UserProfile newUserProfile;

	/**
	 * Constructor UserSettingsChangedPacket Empty to rebuild the packet.
	 */
	public UserSettingsChangedPacket() {
		this(0, 0, 0, 0, null);
	}


	/**
	 * Constructor UserSettingsChangedPacket
	 * Should only be used by the UserManager.
	 *
	 * @param changed
	 * @param newUserProfile
	 */
	public UserSettingsChangedPacket(int[] changed, UserProfile newUserProfile) {
		this(changed[0], changed[1], changed[2], changed[3], newUserProfile);
	}

	/**
	 * Constructor UserSettingsChangedPacket
	 *
	 * @param usernameChanged int.
	 * @param emailChanged    int.
	 * @param passwordChanged int.
	 * @param imageUrlChange  int.
	 * @param newUserProfile  UserProfile.
	 */
	public UserSettingsChangedPacket(int usernameChanged, int emailChanged, int passwordChanged, int imageUrlChange, UserProfile newUserProfile) {
		this.usernameChanged = usernameChanged;
		this.emailChanged = emailChanged;
		this.passwordChanged = passwordChanged;
		this.imageUrlChange = imageUrlChange;
		this.newUserProfile = newUserProfile;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) this.usernameChanged);
		buffer.write((byte) this.emailChanged);
		buffer.write((byte) this.passwordChanged);
		buffer.write((byte) this.imageUrlChange);
		this.newUserProfile.write(buffer);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.usernameChanged = buffer.readByte();
		this.emailChanged = buffer.readByte();
		this.passwordChanged = buffer.readByte();
		this.imageUrlChange = buffer.readByte();
		this.newUserProfile.read(buffer);
	}

	/**
	 * Check if Username is Changed.
	 *
	 * @return boolean
	 */
	public boolean isUsernameChanged() {
		return this.usernameChanged == UserManager.CHANGE_DONE;
	}

	/**
	 * Check if Username get error.
	 *
	 * @return boolean
	 */
	public boolean isUsernameError() {
		return this.usernameChanged == UserManager.CHANGE_ERROR;
	}

	/**
	 * Check if Email is Changed.
	 *
	 * @return boolean
	 */
	public boolean isEmailChanged() {
		return this.emailChanged == UserManager.CHANGE_DONE;
	}

	// TODO finish

	/**
	 * Check if Password is Changed.
	 *
	 * @return boolean
	 */
	public boolean isPasswordChanged() {
		return this.passwordChanged == UserManager.CHANGE_DONE;
	}

	/**
	 * Check if Image Url is Changed.
	 *
	 * @return boolean
	 */
	public boolean isImageUrlChange() {
		return this.imageUrlChange == UserManager.CHANGE_DONE;
	}

	/**
	 * Get the New User Profile.
	 *
	 * @return UserProfile
	 */
	public UserProfile getNewUserProfile() {
		return this.newUserProfile;
	}
}