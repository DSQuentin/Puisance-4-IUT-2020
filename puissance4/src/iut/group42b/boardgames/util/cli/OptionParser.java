package iut.group42b.boardgames.util.cli;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class OptionParser {

	private final List<Option> options;

	/**
	 * Constructor OptionParser.
	 */
	public OptionParser() {
		this.options = new ArrayList<>();
	}

	/**
	 * Add option.
	 *
	 * @param option Option.
	 */
	public void addOption(Option option) {
		this.options.add(option);
	}

	/**
	 * Parse all arguments.
	 *
	 * @param args String[].
	 * @throws OptionParseException
	 */
	public void parse(String[] args) throws OptionParseException {
		for (String argument : args) {
			Option option = null;
			String[] keyValue = argument.split("=", 2);

			if (argument.startsWith("--")) {
				String argumentName = keyValue[0].substring(2);

				if (argumentName.isEmpty()) {
					throw new OptionParseException();
				}

				for (Option opt : this.options) {
					if (opt.getLongName().equalsIgnoreCase(argumentName)) {
						option = opt;
						break;
					}
				}
			} else if (argument.startsWith("-")) {
				String argumentName = keyValue[0].substring(1);

				if (argumentName.length() != 1) {
					throw new OptionParseException();
				}

				for (Option opt : this.options) {
					if (opt.getShortName() == argumentName.charAt(0)) {
						option = opt;
						break;
					}
				}
			}

			if (option == null) {
				throw new OptionParseException();
			}

			if (option.isHasValue()) {
				if (keyValue.length < 2) {
					throw new OptionParseException();
				}

				String rawValue = keyValue[1];
				if (rawValue == null || rawValue.isEmpty()) {
					throw new OptionParseException();
				}

				option.setValue(rawValue);
			}

			option.setUsed(true);
		}
	}

	/**
	 * Display help for User in terminal.
	 *
	 * @param printStream
	 */
	public void printHelp(PrintStream printStream) {
		for (Option option : this.options) {
			if (option.getShortName() == (char) 0) {
				printStream.println(String.format("    --%-12s : %s", option.getLongName(), option.getDescription()));
			} else {
				printStream.println(String.format("-%s  --%-12s : %s", option.getShortName(), option.getLongName(), option.getDescription()));
			}
		}
	}
}