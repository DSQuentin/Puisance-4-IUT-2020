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

	/* Constructor */
	public Option(char shortName, String longName, String description) {
		this(shortName, longName, description, false, null, null);
	}

	/* Constructor */
	public Option(char shortName, String longName, String description, Object defaultValue) {
		this(shortName, longName, description, true, defaultValue, defaultValue == null ? null : defaultValue.getClass());
	}

	/* Constructor */
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

	public char getShortName() {
		return shortName;
	}

	public String getLongName() {
		return longName;
	}

	public String getDescription() {
		return description;
	}

	public boolean isHasValue() {
		return hasValue;
	}

	public Class<?> getType() {
		return type;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}