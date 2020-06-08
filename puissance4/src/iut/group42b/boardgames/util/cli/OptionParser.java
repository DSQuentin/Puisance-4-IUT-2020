package iut.group42b.boardgames.util.cli;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class OptionParser {

	private final List<Option> options;

	public OptionParser() {
		this.options = new ArrayList<>();
	}

	public void addOption(Option option) {
		this.options.add(option);
	}

	public void parse(String[] args) throws OptionParseException {
		for (String argument : args) {
			Option option = null;
			String[] keyValue = argument.split("=", 2);

			if (argument.startsWith("--")) {
				String argumentName = keyValue[0].substring(2);

				if (argumentName.isEmpty()) {
					throw new OptionParseException();
				}

				for (Option opt : options) {
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

				for (Option opt : options) {
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

	public void printHelp(PrintStream printStream) {
		for (Option option : options) {
			if (option.getShortName() == (char) 0) {
				printStream.println(String.format("    --%-12s : %s", option.getLongName(), option.getDescription()));
			} else {
				printStream.println(String.format("-%s  --%-12s : %s", option.getShortName(), option.getLongName(), option.getDescription()));
			}
		}
	}
}