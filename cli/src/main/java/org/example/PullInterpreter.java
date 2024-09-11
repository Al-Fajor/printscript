package org.example;

import static org.example.ObserverType.PRINTLN_OBSERVER;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.example.iterators.InterpreterIterator;
import org.example.observer.BrokerObserver;
import org.example.observer.PrintBrokerObserver;
import org.example.observers.ParserObserver;

public class PullInterpreter {

	private final PrintBrokerObserver observer = new PrintBrokerObserver();
	private final org.example.Interpreter interpreter = createInterpreter(observer);

	private org.example.Interpreter createInterpreter(BrokerObserver<String> observer) {
		return new PrintScriptInterpreter(
				Map.ofEntries(Map.entry(PRINTLN_OBSERVER, observer)), List.of());
	}

	public void execute(Scanner src, String version, String path) {
		InterpreterIterator iterator = new InterpreterIterator(src, path, version);
		iterator.addObserver(new ParserObserver(path));
		interpreter.interpret(iterator);
	}
}
