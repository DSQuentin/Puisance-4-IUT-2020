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


	/**
	 * Constructor UserProfile Empty to rebuild the Object.
	 */
	public UserProfile() {
		this(0, null, null, null, false, false, false, null);
	}

	/**
	 * Constructor UserProfile
	 *
	 * @param id           String.
	 * @param username     String.
	 * @param email        String.
	 * @param imageUrl     String.
	 * @param enabled      boolean.
	 * @param connected    boolean.
	 * @param admin        boolean.
	 * @param creationDate String.
	 */
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

	/**
	 * Create a random User Profile.
	 * <p>For testing.</p>
	 *
	 * @return UserProfile.
	 */
	public static UserProfile random() {
		return new UserProfile(-1, "Random", "random@rand.om", "https://i.imgur.com/U8ekCQP.jpg", true, true, false, "Yesterday");
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.id);
		buffer.write(this.username);
		buffer.write(this.email);
		buffer.write(this.imageUrl);
		buffer.write(this.enabled);
		buffer.write(this.connected);
		buffer.write(this.admin);
		buffer.write(this.creationDate);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.id = buffer.readInt();
		this.username = buffer.readString();
		this.email = buffer.readString();
		this.imageUrl = buffer.readString();
		this.enabled = buffer.readBoolean();
		this.connected = buffer.readBoolean();
		this.admin = buffer.readBoolean();
		this.creationDate = buffer.readString();
	}

	/**
	 * Get the User ID.
	 *
	 * @return int.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Get the Username.
	 *
	 * @return String.
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Get the Email.
	 *
	 * @return String.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Get the Image Url.
	 *
	 * @return String.
	 */
	public String getImageUrl() {
		return this.imageUrl;
	}

	/**
	 * Get the Creation Date.
	 *
	 * @return String.
	 */
	public String getCreationDate() {
		return this.creationDate;
	}

	/**
	 * Check if User is Enabled.
	 *
	 * @return boolean.
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/***
	 * Check if user is connected.
	 * @return boolean.
	 */
	public boolean isConnected() {
		return this.connected;
	}


	/**
	 * Check if user is Admin.
	 *
	 * @return boolean
	 */
	public boolean isAdmin() {
		return this.admin;
	}


	@Override
	public String toString() {
		return "UserProfile{" +
				"id=" + this.id +
				", username='" + this.username + '\'' +
				", email='" + this.email + '\'' +
				", imageUrl='" + this.imageUrl + '\'' +
				", enabled=" + this.enabled +
				", connected=" + this.connected +
				", admin=" + this.admin +
				'}';
	}

}