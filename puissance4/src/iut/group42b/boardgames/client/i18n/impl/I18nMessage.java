package iut.group42b.boardgames.client.i18n.impl;

import iut.group42b.boardgames.client.i18n.IMessage;

import java.util.ResourceBundle;

public class I18nMessage implements IMessage {

	/* Globals */
	private static ResourceBundle resourceBundle;

	/* Variables */
	private final String key;

	/* Constructor */
	public I18nMessage(String key) {
		this.key = key;
	}

	@Override
	public String use(Object... args) {
		if (resourceBundle == null) {
			return key;
		}

		return String.format(resourceBundle.getString(key), args);
	}

	public static void setGlobalResourceBundle(ResourceBundle resourceBundle) {
		I18nMessage.resourceBundle = resourceBundle;
	}

	public static ResourceBundle getGlobalResourceBundle() {
		return resourceBundle;
	}
}