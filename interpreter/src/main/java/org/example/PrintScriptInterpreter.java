package org.example;

import static org.example.ObserverType.PRINTLN_OBSERVER;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.example.ast.statement.Statement;
import org.example.function.PrintlnFunction;
import org.example.function.ReadEnvFunction;
import org.example.observer.BrokerObserver;
import org.example.state.EnvironmentState;
import org.example.state.PrintScriptState;
import org.example.state.StatePriorityList;
import org.example.visitors.StatementVisitor;

public class PrintScriptInterpreter implements Interpreter {
	private final org.example.ast.visitor.StatementVisitor<Void> statementVisitor;

	public PrintScriptInterpreter(
			Map<ObserverType, BrokerObserver<?>> observers, List<Variable<?>> envVariables) {
		StatePriorityList statePriorityList = new StatePriorityList();
		statePriorityList.addState(new PrintScriptState());

		addNativeFunctions(observers, envVariables, statePriorityList);

		statementVisitor = new StatementVisitor(statePriorityList);
	}

	@Override
	public void interpret(Iterator<Statement> statements) {
		while (statements.hasNext()) {
			(statements.next()).accept(statementVisitor);
		}
	}

	private static void addNativeFunctions(
			Map<ObserverType, BrokerObserver<?>> observers,
			List<Variable<?>> envVariables,
			StatePriorityList statePriorityList) {
		statePriorityList.addFunction(
				new PrintlnFunction(
						statePriorityList,
						(BrokerObserver<String>) observers.get(PRINTLN_OBSERVER)));
		statePriorityList.addFunction(
				new ReadEnvFunction(statePriorityList, new EnvironmentState(envVariables)));
	}
}
