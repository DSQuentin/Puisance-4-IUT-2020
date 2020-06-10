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
	private UserProfile newUserProfile;

	/* Constructor */
	public UserSettingsChangedPacket() {
		this(0, 0, 0, 0, null);
	}

	/* Constructor */

	/**
	 * Should only be used by the UserManager.
	 *
	 * @param changed
	 * @param newUserProfile
	 */
	public UserSettingsChangedPacket(int[] changed, UserProfile newUserProfile) {
		this(changed[0], changed[1], changed[2], changed[3], newUserProfile);
	}

	/* Constructor */
	public UserSettingsChangedPacket(int usernameChanged, int emailChanged, int passwordChanged, int imageUrlChange, UserProfile newUserProfile) {
		this.usernameChanged = usernameChanged;
		this.emailChanged = emailChanged;
		this.passwordChanged = passwordChanged;
		this.imageUrlChange = imageUrlChange;
		this.newUserProfile = newUserProfile;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) usernameChanged);
		buffer.write((byte) emailChanged);
		buffer.write((byte) passwordChanged);
		buffer.write((byte) imageUrlChange);
		newUserProfile.write(buffer);
	}

	@Override
	public void read(DataBuffer buffer) {
		usernameChanged = buffer.readByte();
		emailChanged = buffer.readByte();
		passwordChanged = buffer.readByte();
		imageUrlChange = buffer.readByte();
		newUserProfile.read(buffer);
	}

	public boolean isUsernameChanged() {
		return usernameChanged == UserManager.CHANGE_DONE;
	}

	public boolean isUsernameError() {
		return usernameChanged == UserManager.CHANGE_ERROR;
	}

	public boolean isEmailChanged() {
		return emailChanged == UserManager.CHANGE_DONE;
	}

	// TODO finish

	public boolean isPasswordChanged() {
		return passwordChanged == UserManager.CHANGE_DONE;
	}

	public boolean isImageUrlChange() {
		return imageUrlChange == UserManager.CHANGE_DONE;
	}

	public UserProfile getNewUserProfile() {
		return newUserProfile;
	}
}