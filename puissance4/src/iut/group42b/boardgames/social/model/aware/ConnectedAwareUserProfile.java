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

	/* Constructor */
	public ConnectedAwareUserProfile() {
		this(new UserProfile());
	}

	/* Constructor */
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

	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	public BooleanProperty connectedProperty() {
		return this.connectedProperty;
	}

}