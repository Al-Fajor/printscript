package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.example.commands.AnalyzeCommand;
import org.example.commands.Command;
import org.example.commands.ExecutionCommand;
import org.example.commands.FormattingCommand;
import org.example.commands.HelpCommand;
import org.example.commands.ValidationCommand;

public class Cli {
	private static final Map<String, Command> commands;
	private static final String[] NO_ARGS = new String[] {};

	static {
		ExecutionCommand execute = new ExecutionCommand();
		commands =
				new HashMap<>(
						Map.of(
								"validate", new ValidationCommand(),
								"execute", execute,
								"exec", execute,
								"format", new FormattingCommand(),
								"analyze", new AnalyzeCommand()));

		HelpCommand help = new HelpCommand(new HashSet<>(commands.values()));
		commands.put("help", help);
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			commands.get("help").execute(NO_ARGS);
		} else if (commands.containsKey(args[0])) {
			commands.get(args[0]).execute(getCommandArguments(args));
		} else {
			System.out.println(args[0] + " is not a valid command");
		}
	}

	private static String[] getCommandArguments(String[] args) {
		return Arrays.copyOfRange(args, 1, args.length);
	}
}
