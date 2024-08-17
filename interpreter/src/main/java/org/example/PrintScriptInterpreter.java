package org.example;

import java.util.List;
import org.example.ast.AstComponent;
import org.example.ast.statement.Statement;
import org.example.visitors.StatementVisitor;

public class PrintScriptInterpreter implements Interpreter {
	private final InterpreterState state;
	private final org.example.ast.visitor.StatementVisitor<Void> statementVisitor;

	public PrintScriptInterpreter() {
		state = new PrintScriptState();
		statementVisitor = new StatementVisitor(state);
	}

	public PrintScriptInterpreter(InterpreterState state) {
		this.state = state;
		statementVisitor = new StatementVisitor(state);
	}

	@Override
	public void interpret(List<AstComponent> astList) {
		for (AstComponent statement : astList) {
			((Statement) statement).accept(statementVisitor); // TODO avoid cast
		}
	}
}
