package org.example;

import java.util.Iterator;
import org.example.ast.AstComponent;
import org.example.ast.statement.Statement;
import org.example.visitors.StatementVisitor;

public class PrintScriptInterpreter implements Interpreter {
	private final org.example.ast.visitor.StatementVisitor<Void> statementVisitor;

	public PrintScriptInterpreter(InterpreterState state) {
		statementVisitor = new StatementVisitor(state);
	}

	public void interpret(Iterator<AstComponent> statements) {
		while (statements.hasNext()) {
			((Statement) statements.next()).accept(statementVisitor);
		}
	}
}
