package org.example.commands;

@FunctionalInterface
public interface Command {
	void execute(String[] args);
}
