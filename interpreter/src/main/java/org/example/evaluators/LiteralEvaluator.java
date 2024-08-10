package org.example.evaluators;
import org.example.AstComponent;
import org.example.Literal;

public class LiteralEvaluator implements ExpressionEvaluator {
    @Override
    public ExpressionResult evaluate(AstComponent component) {
        Literal<?> literal = (Literal<?>) component;
        switch (literal.getValue()) {
            case String s -> {
                return new ExpressionResult(s);
            }
            case Number n -> {
                return new ExpressionResult(n.doubleValue());
            }
            default ->
                throw new IllegalArgumentException("invalidComponent");
        }
    }
}
