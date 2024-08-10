package org.example.evaluators;

import org.example.ast.AstComponent;
import org.example.InterpreterState;

public class ConditionalEvaluator implements ExpressionEvaluator {
    private final InterpreterState state;

    public ConditionalEvaluator(InterpreterState state) {
        this.state = state;
    }
    @Override
    public ExpressionResult evaluate(AstComponent component) {
        return null; //TODO implement
    }
}
