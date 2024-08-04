package interpreter.strategies.evaluators;

import model.AstComponent;
import model.Literal;

public class LiteralEvaluator implements ExpressionEvaluator {
    @Override
    public ExpressionResult evaluate(AstComponent component) {
        Literal<?> literal = (Literal<?>) component;
        switch (literal.getValue()) {
            case String s -> {
                return new ExpressionResult(s);
            }
            case Double d -> {
                return new ExpressionResult(d);
            }
            default ->
                throw new IllegalArgumentException("invalidComponent");
        }
    }
}
