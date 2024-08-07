package interpreter.evaluators;

import interpreter.InterpreterState;
import model.AstComponent;

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
