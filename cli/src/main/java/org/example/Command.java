package org.example;

@FunctionalInterface
public interface Command {
	void execute(String[] args);
}
