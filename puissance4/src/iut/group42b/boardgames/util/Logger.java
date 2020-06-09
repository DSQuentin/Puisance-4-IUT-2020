package iut.group42b.boardgames.util;

public class Logger {

	/* Variables */
	private final String name;

	/* Constructor */
	public Logger(Class<?> clazz) {
		this(clazz.getSimpleName());
	}

	/* Constructor */
	public Logger(String name) {
		this.name = name;
	}

	public void verbose(String message, Object... args) {
		log(Level.VERBOSE, message, args);
	}

	public void debug(String message, Object... args) {
		log(Level.DEBUG, message, args);
	}

	public void info(String message, Object... args) {
		log(Level.INFO, message, args);
	}

	public void warning(String message, Object... args) {
		log(Level.WARNING, message, args);
	}

	public void error(String message, Object... args) {
		log(Level.ERROR, message, args);
	}

	public void critical(String message, Object... args) {
		log(Level.CRITICAL, message, args);

		System.exit(99);
	}

	public void log(Level level, String message, Object... args) {
		if (level != null && level.isEnabled()) {
			System.out.println(String.format("[ %s / \033[%sm%s\033[49m\033[39m ] %s", name, level.getBashColorCode(), level, String.format(message, args)));
		}
	}

	/**
	 * https://misc.flogisoft.com/bash/tip_colors_and_formatting
	 */
	public enum Level {

		VERBOSE(37),
		DEBUG(96),
		INFO(92),
		WARNING(93),
		ERROR(91),
		CRITICAL(95);

		/* Variables */
		private final int bashColorCode;
		private boolean enabled;

		/* Constructor */
		private Level(int bashColorCode) {
			this.bashColorCode = bashColorCode;
			this.enabled = true;
		}

		public int getBashColorCode() {
			return bashColorCode;
		}

		/**
		 * Enable this logging level globally.
		 */
		public void enable() {
			this.enabled = true;
		}

		/**
		 * Disable this logging level globally.
		 */
		public void disable() {
			this.enabled = false;
		}

		/**
		 * @return The global enabled state of this level.
		 */
		public boolean isEnabled() {
			return enabled;
		}

	}

}
