package org.example.commands;

import java.util.List;
import java.util.concurrent.Callable;
import org.example.Function;
import org.example.Interpreter;
import org.example.Parser;
import org.example.StateListener;
import org.example.Variable;
import org.example.ast.AstComponent;
import org.example.factory.InterpreterFactory;
import org.example.io.Color;
import org.example.observer.PrintBrokerObserver;
import org.example.observer.PrintSubscriber;
import picocli.CommandLine;

@CommandLine.Command(
		name = "execute",
		description =
				"Looks for lexical, syntactic or semantic errors in the file and executes the code")
public class ExecutionCommand implements Callable<Integer> {
	Parser parser = new Parser();
	Interpreter interpreter;

	{
		PrintSubscriber printSubscriber = new PrintSubscriber();
		StateListener stateListener = getDummyListener();
		interpreter = createInterpreter(stateListener, printSubscriber);
	}

	@CommandLine.Parameters(index = "0", description = "The file to be executed.")
	private String file;

	@Override
	public Integer call() {
		List<AstComponent> astList = parser.parse(file);

		if (!astList.isEmpty()) {
			System.out.println("Completed validation successfully. No errors found.");

			Color.printGreen("Running...");
			interpreter.interpret(astList);
			return 0;
		}

		return 1;
	}

	// TODO: duplicate from InterpreterTester
	private Interpreter createInterpreter(
			StateListener stateListener, PrintSubscriber printSubscriber) {
		InterpreterFactory factory = new InterpreterFactory();
		factory.setStateListener(stateListener);

		PrintBrokerObserver printObserver = new PrintBrokerObserver();
		printObserver.addSubscriber(printSubscriber);
		factory.addObserver(printObserver);

		return factory.create();
	}

	private static StateListener getDummyListener() {
		return new StateListener() {
			@Override
			public void updateVariable(Variable<?> variable) {}

			@Override
			public void updateFunction(Function function) {}
		};
	}
}
