package org.example.strategies;

import org.example.ast.AstComponent;
import org.example.InterpreterState;

public class ParametersStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public ParametersStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {

    }
}
