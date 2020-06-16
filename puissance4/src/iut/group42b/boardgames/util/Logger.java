package iut.group42b.boardgames.util;

public class Logger {

	/* Variables */
	private final String name;


	/**
	 * Constructor Logger.
	 *
	 * @param clazz
	 */
	public Logger(Class<?> clazz) {
		this(clazz.getSimpleName());
	}


	/**
	 * Constructor Logger.
	 *
	 * @param name String
	 */
	public Logger(String name) {
		this.name = name;
	}

	/**
	 * Verbose mode.
	 *
	 * @param message String
	 * @param args    Object...
	 */
	public void verbose(String message, Object... args) {
		this.log(Level.VERBOSE, message, args);
	}

	/**
	 * Debug mode.
	 *
	 * @param message String
	 * @param args    Object...
	 */
	public void debug(String message, Object... args) {
		this.log(Level.DEBUG, message, args);
	}

	/**
	 * Info mode.
	 *
	 * @param message String
	 * @param args    Object...
	 */
	public void info(String message, Object... args) {
		this.log(Level.INFO, message, args);
	}

	/**
	 * Warning mode.
	 *
	 * @param message String
	 * @param args    Object...
	 */
	public void warning(String message, Object... args) {
		this.log(Level.WARNING, message, args);
	}

	/**
	 * Error mode.
	 *
	 * @param message String
	 * @param args    Object...
	 */
	public void error(String message, Object... args) {
		this.log(Level.ERROR, message, args);
	}

	/**
	 * Critical mode.
	 *
	 * @param message String
	 * @param args    Object...
	 */
	public void critical(String message, Object... args) {
		this.log(Level.CRITICAL, message, args);

		System.exit(99);
	}

	/**
	 * @param level   Level
	 * @param message String
	 * @param args    Object...
	 */
	public void log(Level level, String message, Object... args) {
		if (level != null && level.isEnabled()) {
			System.out.println(String.format("[ %s / \033[%sm%s\033[49m\033[39m ] %s", this.name, level.getBashColorCode(), level, String.format(message, args)));
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

		/**
		 * Constructor Level.
		 *
		 * @param bashColorCode int.
		 */
		Level(int bashColorCode) {
			this.bashColorCode = bashColorCode;
			this.enabled = true;
		}

		/**
		 * Get Bash Color Code.
		 *
		 * @return int
		 */
		public int getBashColorCode() {
			return this.bashColorCode;
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
			return this.enabled;
		}

	}

}
