package iut.group42b.boardgames.client.i18n.impl;

import iut.group42b.boardgames.client.i18n.IMessage;

public class FormattableMessage implements IMessage {

	/* Variables */
	private final String format;


	/**
	 * Constructor FormattableMessage.
	 *
	 * @param format
	 */
	public FormattableMessage(String format) {
		this.format = format;
	}

	@Override
	public String use(Object... args) {
		return String.format(this.format, args);
	}

}