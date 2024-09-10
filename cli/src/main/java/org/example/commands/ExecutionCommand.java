package org.example.commands;

import java.util.Scanner;
import java.util.concurrent.Callable;
import org.example.Interpreter;
import org.example.PullInterpreter;
import org.example.factory.InterpreterFactory;
import org.example.io.ScriptReader;
import org.example.observer.BrokerObserver;
import org.example.observer.PrintBrokerObserver;
import picocli.CommandLine;

@CommandLine.Command(
		name = "execute",
		description =
				"Looks for lexical, syntactic or semantic errors in the file and executes the code")
public class ExecutionCommand implements Callable<Integer> {
	Interpreter interpreter;

	{
		BrokerObserver<String> brokerObserver = new PrintBrokerObserver();
		interpreter = createInterpreter(brokerObserver);
	}

	@CommandLine.Parameters(index = "0", description = "The path of the file to be executed.")
	private String filePath;

	@CommandLine.Option(
			names = "--version",
			description = "The PrintScript version of the code being executed",
			defaultValue = "1.0")
	private String version;

	@Override
	public Integer call() {
		PullInterpreter pullInterpreter = new PullInterpreter();
		Scanner scanner = ScriptReader.readCodeFromSourceByLine(filePath);

		pullInterpreter.execute(scanner, version, filePath);
		return 1;
	}

	private Interpreter createInterpreter(BrokerObserver<String> printObserver) {
		InterpreterFactory factory = new InterpreterFactory();

		factory.addObserver(printObserver);
		return factory.create();
	}
}
