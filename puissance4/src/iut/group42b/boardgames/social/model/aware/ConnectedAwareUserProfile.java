package iut.group42b.boardgames.social.model.aware;

import iut.group42b.boardgames.network.rw.IWritableReadableObject;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ConnectedAwareUserProfile implements IWritableReadableObject {

	/* Variables */
	private final UserProfile userProfile;
	private final BooleanProperty connectedProperty;


	/**
	 * Constructor ConnectedAwareUserProfile Empty to rebuild
	 */
	public ConnectedAwareUserProfile() {
		this(new UserProfile());
	}


	/**
	 * Constructor ConnectedAwareUserProfile
	 *
	 * @param userProfile
	 */
	public ConnectedAwareUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
		this.connectedProperty = new SimpleBooleanProperty(userProfile.isConnected());
	}

	@Override
	public void write(DataBuffer buffer) {
		this.userProfile.write(buffer);
		buffer.write(this.connectedProperty.get());
	}

	@Override
	public void read(DataBuffer buffer) {
		this.userProfile.read(buffer);
		this.connectedProperty.set(buffer.readBoolean());
	}

	/**
	 * Get the userprofile.
	 *
	 * @return
	 */
	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	/**
	 * Connected Property
	 *
	 * @return
	 */
	public BooleanProperty connectedProperty() {
		return this.connectedProperty;
	}

}