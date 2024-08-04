package interpreter.strategies.evaluators;

import interpreter.InterpreterState;
import model.*;

public class ComponentEvaluator implements ExpressionEvaluator {
    private final InterpreterState state;

    public ComponentEvaluator(InterpreterState state) {
        this.state = state;
    }

    @Override
    public ExpressionResult evaluate(AstComponent component) {
        switch (component) {
            case BinaryExpression binaryExpression -> {
                return new BinaryExpressionEvaluator(state).evaluate(component);
            }
            case Conditional conditional -> {
                return new ConditionalEvaluator(state).evaluate(component);
            }
            case Identifier identifier -> {
                return new IdentifierEvaluator(state).evaluate(component);
            }
            case Literal<?> literal -> {
                return new LiteralEvaluator().evaluate(component);
            }
            default -> throw new IllegalArgumentException("Component cannot be evaluated");
        }
    }
}
