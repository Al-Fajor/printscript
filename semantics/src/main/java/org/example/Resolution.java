package org.example;

import model.AstComponent;
import model.DeclarationType;

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
