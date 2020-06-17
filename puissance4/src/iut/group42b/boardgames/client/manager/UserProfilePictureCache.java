package iut.group42b.boardgames.client.manager;

import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.Logger;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class UserProfilePictureCache {

	/* Logger */
	private final static Logger LOGGER = new Logger(UserProfilePictureCache.class);

	/* Singleton */
	private final static UserProfilePictureCache INSTANCE = new UserProfilePictureCache();

	/* Variables */
	private final Map<String, Image> images;

	/* Constructor */
	private UserProfilePictureCache() {
		this.images = new HashMap<>();
	}

	public Image auto(UserProfile userProfile) {
		return this.auto(userProfile.getImageUrl());
	}

	public Image auto(String imageUrl) {
		synchronized (this.images) {
			return this.images.computeIfAbsent(imageUrl, (url) -> new Image(url, true));
		}
	}

	public void clear() {
		synchronized (this.images) {
			this.images.clear();
		}
	}

	public static UserProfilePictureCache get() {
		return INSTANCE;
	}

}