package org.example;

import static org.example.observer.ObserverType.PRINTLN_OBSERVER;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.example.ast.statement.Statement;
import org.example.function.PrintlnFunction;
import org.example.function.ReadEnvFunction;
import org.example.function.ReadInputFunction;
import org.example.observer.BrokerObserver;
import org.example.observer.ObserverType;
import org.example.state.EnvironmentState;
import org.example.state.PrintScriptState;
import org.example.state.StatePriorityList;
import org.example.visitors.StatementVisitor;

public class PrintScriptInterpreter implements Interpreter {
	private final org.example.ast.visitor.StatementVisitor<Void> statementVisitor;

	public PrintScriptInterpreter(
			Map<ObserverType, BrokerObserver<?>> observers,
			List<Variable<?>> envVariables,
			InputListener inputListener) {
		StatePriorityList statePriorityList = new StatePriorityList();
		statePriorityList.addState(new PrintScriptState());

		addNativeFunctions(observers, envVariables, statePriorityList, inputListener);

		statementVisitor = new StatementVisitor(statePriorityList);
	}

	@Override
	public void interpret(Iterator<Statement> statements) {
		while (statements.hasNext()) {
			Statement next = statements.next();
			next.accept(statementVisitor);
		}
	}

	private static void addNativeFunctions(
			Map<ObserverType, BrokerObserver<?>> observers,
			List<Variable<?>> envVariables,
			StatePriorityList statePriorityList,
			InputListener inputListener) {
		statePriorityList.addFunction(
				new PrintlnFunction(
						statePriorityList,
						(BrokerObserver<String>) observers.get(PRINTLN_OBSERVER)));
		statePriorityList.addFunction(
				new ReadEnvFunction(statePriorityList, new EnvironmentState(envVariables)));
		statePriorityList.addFunction(new ReadInputFunction(inputListener, statePriorityList));
	}
}
