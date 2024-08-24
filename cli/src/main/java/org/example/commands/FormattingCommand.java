package org.example.commands;

public class FormattingCommand implements Command {
	@Override
	public void execute(String[] args) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String getSyntax() {
		return "format <filePath> --version <version> --config <configFilePath>";
	}

	@Override
	public String getDescription() {
		return "Modifies the file to make code cleaner, without changing functionality";
	}
}
