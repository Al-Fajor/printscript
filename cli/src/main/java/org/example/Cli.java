package org.example;

import org.example.commands.AnalyzeCommand;
import org.example.commands.ExecutionCommand;
import org.example.commands.FormattingCommand;
import org.example.commands.ValidationCommand;
import picocli.CommandLine;

@CommandLine.Command(
		name = "pts",
		subcommands = {
			ValidationCommand.class,
			ExecutionCommand.class,
			FormattingCommand.class,
			AnalyzeCommand.class
		},
		mixinStandardHelpOptions = true)
public class Cli implements Runnable {
	public static void main(String[] args) {
		CommandLine.run(new Cli(), args);
	}

	@Override
	public void run() {
		System.out.println("Please specify a command.");
	}
}
