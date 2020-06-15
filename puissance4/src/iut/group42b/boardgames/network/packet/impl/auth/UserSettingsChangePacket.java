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

	/* Constructor */
	public UserSettingsChangePacket() {
		this(null, null, null, null);
	}


	/* Constructor */
	public UserSettingsChangePacket(String email, String username, String password, String imageUrl) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.imageUrl = imageUrl;

		this.numberOfUpdatedField = (email != null ? 1 : 0) + (username != null ? 1 : 0) + (password != null ? 1 : 0) + (imageUrl != null ? 1 : 0);

	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(numberOfUpdatedField);

		if (email != null) {
			buffer.write(TYPE_EMAIL).write(email);
		}

		if (username != null) {
			buffer.write(TYPE_USERNAME).write(username);
		}

		if (password != null) {
			buffer.write(TYPE_PASSWORD).write(password);
		}

		if (imageUrl != null) {
			buffer.write(TYPE_IMAGE_URL).write(imageUrl);
		}
	}

	@Override
	public void read(DataBuffer buffer) {
		this.numberOfUpdatedField = buffer.readInt();

		for (int i = 0; i < numberOfUpdatedField; i++) {
			int type = buffer.readInt();
			String value = buffer.readString();

			switch (type) {
				case TYPE_EMAIL:
					email = value;
					break;

				case TYPE_USERNAME:
					username = value;
					break;

				case TYPE_PASSWORD:
					password = value;
					break;

				case TYPE_IMAGE_URL:
					imageUrl = value;
					break;

				default:
					LOGGER.error("Unknown type: %s", type);
					break;
			}
		}
	}

	public int getNumberOfUpdatedField() {
		return numberOfUpdatedField;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}
