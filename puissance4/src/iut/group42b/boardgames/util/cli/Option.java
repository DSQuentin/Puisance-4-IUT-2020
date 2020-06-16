package iut.group42b.boardgames.util.cli;

public class Option {

	/* Variables  */
	private final char shortName;
	private final String longName, description;
	private final boolean hasValue;
	private final Class<?> type;
	private final Object defaultValue;

	private Object value;
	private boolean used;


	/**
	 * Constructor Option.
	 *
	 * @param shortName   char.
	 * @param longName    String.
	 * @param description String.
	 */
	public Option(char shortName, String longName, String description) {
		this(shortName, longName, description, false, null, null);
	}


	/**
	 * Constructor Option.
	 *
	 * @param shortName    char.
	 * @param longName     String.
	 * @param description  String.
	 * @param defaultValue Object.
	 */
	public Option(char shortName, String longName, String description, Object defaultValue) {
		this(shortName, longName, description, true, defaultValue, defaultValue == null ? null : defaultValue.getClass());
	}


	/**
	 * Constructor Option
	 *
	 * @param shortName    char.
	 * @param longName     String.
	 * @param description  String.
	 * @param hasValue     boolean.
	 * @param defaultValue Object.
	 * @param type         Class<?>.
	 */
	public Option(char shortName, String longName, String description, boolean hasValue, Object defaultValue, Class<?> type) {
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
		this.hasValue = hasValue;
		this.type = type;
		this.defaultValue = defaultValue;

		this.value = defaultValue;
		this.used = false;
	}

	/**
	 * Get the Short Name of Option.
	 *
	 * @return char.
	 */
	public char getShortName() {
		return this.shortName;
	}

	/**
	 * Get the long name of option.
	 *
	 * @return String.
	 */
	public String getLongName() {
		return this.longName;
	}

	/**
	 * Get the Description of option.
	 *
	 * @return String.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Check if options have value.
	 *
	 * @return boolean.
	 */
	public boolean isHasValue() {
		return this.hasValue;
	}

	/**
	 * Get Type.
	 *
	 * @return Class<?>.
	 */
	public Class<?> getType() {
		return this.type;
	}

	/**
	 * Get the Default Value.
	 *
	 * @return Object.
	 */
	public Object getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Get the value.
	 *
	 * @return Object.
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Set the Value.
	 *
	 * @param value Object.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Check if options is used.
	 *
	 * @return boolean.
	 */
	public boolean isUsed() {
		return this.used;
	}

	/**
	 * Set Used.
	 *
	 * @param used boolean.
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}

}