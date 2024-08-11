package org.example;

import org.example.ast.AstComponent;

public record Resolution(
        SemanticResult result,
        AstComponent resolvedAst
) {
    public static Resolution failure(String reason) {
        return new Resolution(
                new SemanticFailure(reason),
                null
        );
    }
}
