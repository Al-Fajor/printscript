package interpreter.strategies.evaluators;

import model.*;

public class ComponentEvaluator implements ExpressionEvaluator {

    @Override
    public ExpressionResult evaluate(AstComponent component) {
        switch (component) {
            case BinaryExpression binaryExpression -> {
                return new BinaryExpressionEvaluator().evaluate(component);
            }
            case Conditional conditional -> {
                return new ConditionalEvaluator().evaluate(component);
            }
            case Identifier identifier -> {
                return new IdentifierEvaluator().evaluate(component);
            }
            case Literal literal -> {
                return new LiteralEvaluator().evaluate(component);
            }
            default -> throw new IllegalArgumentException("Component cannot be evaluated");
        }
    }
}
