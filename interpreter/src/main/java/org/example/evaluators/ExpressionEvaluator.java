package org.example.evaluators;

import org.example.ast.AstComponent;

public interface ExpressionEvaluator {
    ExpressionResult evaluate(AstComponent component);
}
