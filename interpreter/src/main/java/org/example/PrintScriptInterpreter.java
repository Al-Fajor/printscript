package org.example;

import static org.example.ObserverType.PRINTLN_OBSERVER;

import java.util.Iterator;
import java.util.Map;
import org.example.ast.statement.Statement;
import org.example.observer.BrokerObserver;
import org.example.visitors.StatementVisitor;

public class PrintScriptInterpreter implements Interpreter {
	private final org.example.ast.visitor.StatementVisitor<Void> statementVisitor;

	public PrintScriptInterpreter(Map<ObserverType, BrokerObserver<?>> observers) {
		StatePriorityList statePriorityList = new StatePriorityList();
		statePriorityList.addState(new PrintScriptState());
		statePriorityList.addFunction(
				new PrintlnFunction(
						statePriorityList,
						(BrokerObserver<String>) observers.get(PRINTLN_OBSERVER)));

		statementVisitor = new StatementVisitor(statePriorityList);
	}

	public void interpret(Iterator<Statement> statements) {
		while (statements.hasNext()) {
			(statements.next()).accept(statementVisitor);
		}
	}
}
