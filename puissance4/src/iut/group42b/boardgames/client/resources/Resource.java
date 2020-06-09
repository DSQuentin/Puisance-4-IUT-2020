package iut.group42b.boardgames.client.resources;

import iut.group42b.boardgames.util.Logger;
import javafx.scene.image.Image;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public class Resource {

	/* Constants */
	public static final Locale DEFAULT_LOCALE_ENGLISH = new Locale("en", "US");
	public static final Locale DEFAULT_LOCALE_FRENCH = new Locale("fr", "FR");

	/* Logger */
	private static final Logger LOGGER = new Logger(Resource.class);

	public static final URL loadForm(String name) throws Exception {
		LOGGER.verbose("Loading form: %s", name);

		return Resource.class.getResource("./forms/" + name);
	}

	public static final ResourceBundle loadResourceBundle(String baseName) throws Exception {
		return loadResourceBundle(baseName, DEFAULT_LOCALE_ENGLISH);
	}

	/**
	 * Source: https://stackoverflow.com/a/15654598
	 */
	public static final ResourceBundle loadResourceBundle(String baseName, Locale locale) throws Exception {
		LOGGER.verbose("Loading resource bundle: %s (%s)", baseName, locale);

		return ResourceBundle.getBundle(baseName, locale, new URLClassLoader(
				new java.net.URL[]{Resource.class.getResource("./bundles/").toURI().toURL()}
		));
	}

	public static Image loadImage(String name) {
		LOGGER.verbose("Loading image : %s ", name);
		return new Image(Resource.class.getResourceAsStream("images/" + name));
	}

}