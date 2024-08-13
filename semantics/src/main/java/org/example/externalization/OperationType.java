package org.example.externalization;

import org.example.ast.BinaryOperator;
import org.example.ast.DeclarationType;

public record OperationType(
        DeclarationType type1,
        BinaryOperator operator,
        DeclarationType type2
) {
}
