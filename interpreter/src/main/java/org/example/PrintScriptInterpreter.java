package org.example;

import org.example.ast.AstComponent;
import org.example.ast.statement.SentenceStatement;
import org.example.ast.visitor.Visitor;
import org.example.visitors.StatementVisitor;

import java.util.List;

public class PrintScriptInterpreter implements Interpreter {
    private final InterpreterState state;
    private final Visitor<Void> statementVisitor;

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
            statement.accept(statementVisitor);
        }
    }
}
