package iut.group42b.boardgames.social.model;

import iut.group42b.boardgames.network.rw.IWritableReadableObject;
import iut.group42b.boardgames.util.DataBuffer;

public class UserProfile implements IWritableReadableObject {

	/* Variables */
	private int id;
	private String username;
	private String email;
	private String imageUrl;
	private boolean enabled;
	private boolean connected;
	private boolean admin;
	private String creationDate;

	/* Constructor */
	public UserProfile() {
		this(0, null, null, null, false, false, false,null);
	}

	/* Constructor */
	public UserProfile(int id, String username, String email, String imageUrl, boolean enabled, boolean connected, boolean admin, String creationDate) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.imageUrl = imageUrl;
		this.enabled = enabled;
		this.connected = connected;
		this.admin = admin;
		this.creationDate = creationDate;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(id);
		buffer.write(username);
		buffer.write(email);
		buffer.write(imageUrl);
		buffer.write(enabled);
		buffer.write(connected);
		buffer.write(admin);
		buffer.write(creationDate);
	}

	@Override
	public void read(DataBuffer buffer) {
		id = buffer.readInt();
		username = buffer.readString();
		email = buffer.readString();
		imageUrl = buffer.readString();
		enabled = buffer.readBoolean();
		connected = buffer.readBoolean();
		admin = buffer.readBoolean();
		creationDate = buffer.readString();
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isConnected() {
		return connected;
	}

	public boolean isAdmin() {
		return admin;
	}


	@Override
	public String toString() {
		return "UserProfile{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", enabled=" + enabled +
				", connected=" + connected +
				", admin=" + admin +
				'}';
	}

}