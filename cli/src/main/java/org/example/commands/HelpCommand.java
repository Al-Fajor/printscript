package org.example.commands;

import java.util.Set;

public class HelpCommand implements Command {
	private final Set<Command> commands;

	public HelpCommand(Set<Command> commands) {
		this.commands = commands;
	}

	@Override
	public void execute(String[] args) {
		System.out.println("Available commands: \n");
		commands.forEach(
				command ->
						System.out.println(command.getSyntax() + " | " + command.getDescription()));
	}

	@Override
	public String getSyntax() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Show information about available commands";
	}
}
