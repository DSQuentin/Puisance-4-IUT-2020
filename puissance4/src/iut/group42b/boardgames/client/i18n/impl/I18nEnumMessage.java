package iut.group42b.boardgames.client.i18n.impl;

import iut.group42b.boardgames.client.i18n.IMessage;

/**
 * use([ a, b, c, d, e, f ])
 * // on dit que a = enum, donc la key ca va etre baseKey.a
 * // pour passer le reste array[1:]
 */
public class I18nEnumMessage implements IMessage {

	/* Variables */
	private final String baseKey;

	/* Constructor */
	public I18nEnumMessage(String baseKey) {
		this.baseKey = baseKey;
	}

	@Override
	public String use(Object... args) {
		if (I18nMessage.getGlobalResourceBundle() == null || args.length == 0) {
			return this.baseKey;
		}

		String key = this.baseKey + "." + args[0].toString().toLowerCase();

		Object[] remainingArgs = new Object[args.length - 1];
		System.arraycopy(args, 1, remainingArgs, 0, remainingArgs.length);

		return String.format(I18nMessage.getGlobalResourceBundle().getString(key), remainingArgs);
	}

}