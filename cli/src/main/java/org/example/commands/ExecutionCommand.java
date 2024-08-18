package org.example.commands;

import org.example.Function;
import org.example.Interpreter;
import org.example.Parser;
import org.example.StateListener;
import org.example.Variable;
import org.example.ast.AstComponent;
import org.example.factory.InterpreterFactory;
import org.example.observer.PrintObserver;
import org.example.observer.PrintSubscriber;

import java.util.List;

public class ExecutionCommand implements Command {
    Parser parser = new Parser();
    Interpreter interpreter;
    {
        PrintSubscriber printSubscriber = new PrintSubscriber();
        StateListener stateListener = getDummyListener();
        interpreter = createInterpreter(stateListener, printSubscriber);
    }

    @Override
	public void execute(String[] args) {
        List<AstComponent> astList = parser.parse(args[0]);

        Color.printGreen("Running...");
        interpreter.interpret(astList);
	}

    // TODO: duplicate from InterpreterTester
    private Interpreter createInterpreter(
            StateListener stateListener, PrintSubscriber printSubscriber) {
        InterpreterFactory factory = new InterpreterFactory();
        factory.setStateListener(stateListener);

        PrintObserver printObserver = new PrintObserver();
        printObserver.addSubscriber(printSubscriber);
        factory.addObserver(printObserver);

        return factory.create();
    }

    private static StateListener getDummyListener() {
        return new StateListener() {
            @Override
            public void updateVariable(Variable<?> variable) {

            }

            @Override
            public void updateFunction(Function function) {

            }
        };
    }
}
