package org.example.commands;

public class AnalyzeCommand implements Command {
	@Override
	public void execute(String[] args) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String getSyntax() {
		return "analyze <filePath> --version <version> --config <configFilePath>";
	}

	@Override
	public String getDescription() {
		return "Checks for convention or good practice violations in the file";
	}
}
