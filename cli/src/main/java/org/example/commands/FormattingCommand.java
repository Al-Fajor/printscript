package org.example.commands;

import picocli.CommandLine;

@CommandLine.Command(
		name = "format",
		description = "Modifies the file to make code cleaner, without changing functionality")
public class FormattingCommand {

	@CommandLine.Parameters(index = "0", description = "The file to be formatted.")
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
