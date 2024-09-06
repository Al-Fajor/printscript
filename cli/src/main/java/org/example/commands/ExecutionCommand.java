package org.example.commands;

import java.util.List;
import java.util.concurrent.Callable;
import org.example.Interpreter;
import org.example.Parser;
import org.example.ast.AstComponent;
import org.example.ast.statement.Statement;
import org.example.factory.InterpreterFactory;
import org.example.io.Color;
import org.example.observer.BrokerObserver;
import org.example.observer.PrintBrokerObserver;
import picocli.CommandLine;

@CommandLine.Command(
		name = "execute",
		description =
				"Looks for lexical, syntactic or semantic errors in the file and executes the code")
public class ExecutionCommand implements Callable<Integer> {
	Parser parser = new Parser();
	Interpreter interpreter;

	{
		BrokerObserver<String> brokerObserver = new PrintBrokerObserver();
		interpreter = createInterpreter(brokerObserver);
	}

	@CommandLine.Parameters(index = "0", description = "The file to be executed.")
	private String file;

	@Override
	public Integer call() {
		List<Statement> astList = parser.parse(file);

		if (!astList.isEmpty()) {
			System.out.println("\nCompleted validation successfully. No errors found.");

			Color.printGreen("\nRunning...");
			interpreter.interpret(toAstList(astList).iterator());
			return 0;
		}

		return 1;
	}

	private List<AstComponent> toAstList(List<Statement> statementList) {
		return statementList.stream().map(statement -> (AstComponent) statement).toList();
	}

	// TODO: duplicate from InterpreterTester
	private Interpreter createInterpreter(BrokerObserver<String> printObserver) {
		InterpreterFactory factory = new InterpreterFactory();

		factory.addObserver(printObserver);
		return factory.create();
	}
}
