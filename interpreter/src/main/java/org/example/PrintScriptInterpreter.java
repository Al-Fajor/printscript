package org.example;

import java.util.List;
import org.example.ast.AstComponent;
import org.example.ast.statement.Statement;
import org.example.visitors.StatementVisitor;

public class PrintScriptInterpreter implements Interpreter {
	private final org.example.ast.visitor.StatementVisitor<Void> statementVisitor;

	public PrintScriptInterpreter(InterpreterState state) {
		statementVisitor = new StatementVisitor(state);
	}

	@Override
	public void interpret(List<AstComponent> astList) {
		for (AstComponent statement : astList) {
			((Statement) statement).accept(statementVisitor); // TODO avoid cast
		}
	}
}
