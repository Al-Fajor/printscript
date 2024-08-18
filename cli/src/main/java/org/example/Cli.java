package org.example;

import java.util.Arrays;
import java.util.Map;

public class Cli {
	private static final Map<String, Command> commands =
			Map.of(
					"help", new HelpCommand(),
					"validate", new ValidationCommand(),
					"execute", new ExecutionCommand(),
					"format", new FormattingCommand(),
					"analyze", new AnalyzeCommand());
	public static final String[] NO_PARAMETERS = {};

	public static void main(String[] args) {
		if (args.length == 0) {
			commands.get("help").execute(NO_PARAMETERS);
		} else if (args.length == 1) {
			System.out.println("Command must be of the form '<command> <filePath> <flags>'");
		} else
			commands.getOrDefault(
							args[0],
							(commandArgs) ->
									System.out.println(args[0] + " is not a valid command"))
					.execute(getCommandArguments(args));
	}

	private static String[] getCommandArguments(String[] args) {
		return Arrays.copyOfRange(args, 1, args.length);
	}
}
