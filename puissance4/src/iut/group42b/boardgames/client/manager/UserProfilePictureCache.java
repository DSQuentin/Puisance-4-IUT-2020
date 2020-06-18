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


	/***
	 * Constructor UserProfilePictureCache
	 */
	private UserProfilePictureCache() {
		this.images = new HashMap<>();
	}

	/***
	 * Put image in cache
	 * @param userProfile
	 * @return
	 */
	public Image auto(UserProfile userProfile) {
		return this.auto(userProfile.getImageUrl());
	}

	/***
	 * Put image in cache
	 * @param imageUrl
	 * @return
	 */
	public Image auto(String imageUrl) {
		synchronized (this.images) {
			return this.images.computeIfAbsent(imageUrl, (url) -> new Image(url, true));
		}
	}

	/**
	 * Clear all cached images
	 */
	public void clear() {
		synchronized (this.images) {
			this.images.clear();
		}
	}

	/**
	 * Get the cache.
	 *
	 * @return
	 */
	public static UserProfilePictureCache get() {
		return INSTANCE;
	}

}