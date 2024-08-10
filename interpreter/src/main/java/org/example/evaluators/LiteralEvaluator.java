package org.example.evaluators;
import org.example.ast.AstComponent;
import org.example.ast.Literal;

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
