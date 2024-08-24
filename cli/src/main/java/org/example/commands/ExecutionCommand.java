package org.example.commands;

import java.util.List;
import org.example.Interpreter;
import org.example.Parser;
import org.example.ast.AstComponent;
import org.example.factory.InterpreterFactory;
import org.example.io.Color;
import org.example.observer.BrokerObserver;
import org.example.observer.PrintBrokerObserver;

public class ExecutionCommand implements Command {
	Parser parser = new Parser();
	Interpreter interpreter;

	{
		BrokerObserver<String> brokerObserver = new PrintBrokerObserver();
		interpreter = createInterpreter(brokerObserver);
	}

	@Override
	public void execute(String[] args) {
		List<AstComponent> astList = parser.parse(args[0]);
		if (astList.isEmpty()) return;

		Color.printGreen("Running...");
		interpreter.interpret(astList);
	}

	// TODO: duplicate from InterpreterTester
	private Interpreter createInterpreter(BrokerObserver<String> printObserver) {
		InterpreterFactory factory = new InterpreterFactory();

		factory.addObserver(printObserver);
		return factory.create();
	}
}
