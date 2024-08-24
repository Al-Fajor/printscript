package org.example.commands;

import picocli.CommandLine;

@CommandLine.Command(
		name = "analyze",
		description = "Checks for convention or good practice violations in the file")
public class AnalyzeCommand {
	@CommandLine.Parameters(index = "0", description = "The file to be analyzed.")
	private String file;

	@CommandLine.Option(
			names = "--version",
			description = "The PrintScript version of the code being validate")
	private int version;

	@CommandLine.Option(
			names = "--config",
			description = "The path to the formatting configuration file")
	private String config;
}
