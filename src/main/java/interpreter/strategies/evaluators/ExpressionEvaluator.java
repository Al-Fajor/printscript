package interpreter.strategies.evaluators;

import model.AstComponent;

public interface ExpressionEvaluator {
    ExpressionResult evaluate(AstComponent component);
}
