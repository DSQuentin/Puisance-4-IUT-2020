package iut.group42b.boardgames.social.model.aware;

import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ReadAwareUserProfile extends ConnectedAwareUserProfile {

	/* Variables */
	private final IntegerProperty notReadProperty;

	/* Constructor */
	public ReadAwareUserProfile() {
		this(new UserProfile(), 0);
	}

	/* Constructor */
	public ReadAwareUserProfile(UserProfile userProfile, int notReadCount) {
		super(userProfile);

		this.notReadProperty = new SimpleIntegerProperty(notReadCount);
	}

	@Override
	public void write(DataBuffer buffer) {
		super.write(buffer);

		buffer.write(this.notReadProperty.get());
	}

	@Override
	public void read(DataBuffer buffer) {
		super.read(buffer);

		this.notReadProperty.set(buffer.readInt());
	}

	public IntegerProperty notReadProperty() {
		return this.notReadProperty;
	}

}