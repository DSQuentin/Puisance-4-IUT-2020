package iut.group42b.boardgames.network.packet.impl.auth;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;
import iut.group42b.boardgames.util.Logger;

public class UserSettingsChangePacket implements IPacket {

	/* Logger */
	private final static Logger LOGGER = new Logger(UserSettingsChangePacket.class);

	/* Constants */
	public static final int TYPE_EMAIL = 0;
	public static final int TYPE_USERNAME = 1;
	public static final int TYPE_PASSWORD = 2;
	public static final int TYPE_IMAGE_URL = 3;

	/* Variables */
	private int numberOfUpdatedField;
	private String email;
	private String username;
	private String password;
	private String imageUrl;

	/**
	 * Constructor UserSettingsChangePacket
	 */
	public UserSettingsChangePacket() {
		this(null, null, null, null);
	}


	/**
	 * Constructor UserSettingsChangePacket
	 *
	 * @param email    String
	 * @param username String
	 * @param password String
	 * @param imageUrl String
	 */
	public UserSettingsChangePacket(String email, String username, String password, String imageUrl) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.imageUrl = imageUrl;

		this.numberOfUpdatedField = (email != null ? 1 : 0) + (username != null ? 1 : 0) + (password != null ? 1 : 0) + (imageUrl != null ? 1 : 0);

	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.numberOfUpdatedField);

		if (this.email != null) {
			buffer.write(TYPE_EMAIL).write(this.email);
		}

		if (this.username != null) {
			buffer.write(TYPE_USERNAME).write(this.username);
		}

		if (this.password != null) {
			buffer.write(TYPE_PASSWORD).write(this.password);
		}

		if (this.imageUrl != null) {
			buffer.write(TYPE_IMAGE_URL).write(this.imageUrl);
		}
	}

	@Override
	public void read(DataBuffer buffer) {
		this.numberOfUpdatedField = buffer.readInt();

		for (int i = 0; i < this.numberOfUpdatedField; i++) {
			int type = buffer.readInt();
			String value = buffer.readString();

			switch (type) {
				case TYPE_EMAIL:
					this.email = value;
					break;

				case TYPE_USERNAME:
					this.username = value;
					break;

				case TYPE_PASSWORD:
					this.password = value;
					break;

				case TYPE_IMAGE_URL:
					this.imageUrl = value;
					break;

				default:
					LOGGER.error("Unknown type: %s", type);
					break;
			}
		}
	}

	/**
	 * Get Number Of Updated Fields.
	 *
	 * @return int
	 */
	public int getNumberOfUpdatedField() {
		return this.numberOfUpdatedField;
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
	 * Get the Username.
	 *
	 * @return String.
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Get the password.
	 *
	 * @return String.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Get Image Url.
	 *
	 * @return String.
	 */
	public String getImageUrl() {
		return this.imageUrl;
	}
}
