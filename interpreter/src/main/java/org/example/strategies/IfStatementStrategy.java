package org.example.strategies;

import org.example.InterpreterState;
import org.example.ast.AstComponent;

public class IfStatementStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public IfStatementStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {

    }
}
