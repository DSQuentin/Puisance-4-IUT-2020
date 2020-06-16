package iut.group42b.boardgames.client.resources;

import iut.group42b.boardgames.util.Logger;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Resource {

	/* Constants */
	public static final Locale DEFAULT_LOCALE_ENGLISH = new Locale("en", "US");
	public static final Locale DEFAULT_LOCALE_FRENCH = new Locale("fr", "FR");

	/* Logger */
	private static final Logger LOGGER = new Logger(Resource.class);

	/**
	 * Load FXML forms from resource directory.
	 *
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static URL loadForm(String name) throws Exception {
		LOGGER.verbose("Loading form: %s", name);

		return Resource.class.getResource("forms/" + name);
	}

	/**
	 * Load a resource bundle file with default language (ENGLISH)
	 *
	 * @param baseName
	 * @return
	 * @throws Exception
	 */
	public static final ResourceBundle loadResourceBundle(String baseName) throws Exception {
		return loadResourceBundle(baseName, DEFAULT_LOCALE_ENGLISH);
	}

	/**
	 * Load a resource bundle file
	 *
	 * @param baseName
	 * @param locale   Country code like : FR_fr.
	 * @return
	 * @throws Exception
	 * @see <a href="https://stackoverflow.com/a/15654598"> Source</a>
	 */
	public static final ResourceBundle loadResourceBundle(String baseName, Locale locale) throws Exception {
		LOGGER.verbose("Loading resource bundle: %s (%s)", baseName, locale);

		/*return ResourceBundle.getBundle(baseName, locale, new URLClassLoader(
				new java.net.URL[]{Resource.class.getResource("./bundles/").toURI().toURL()}
		));*/
		return ResourceBundle.getBundle(Resource.class.getPackage().getName() + ".bundles." + baseName, locale);
	}

	/**
	 * Load pictures from resources folder.
	 *
	 * @param name Filename of picture.
	 * @return The loaded image.
	 */
	public static Image loadImage(String name) {
		LOGGER.verbose("Loading image : %s ", name);
		return new Image(Resource.class.getResourceAsStream("images/" + name));
	}

}