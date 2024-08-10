package org.example.evaluators;

import org.example.AstComponent;

public interface ExpressionEvaluator {
    ExpressionResult evaluate(AstComponent component);
}
