package org.example;

import org.example.ast.Declaration;
import org.example.ast.DeclarationType;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public record Resolution(
        SemanticResult result,
        Optional<DeclarationType> evaluatedType,
        boolean isValuePresent,
        Optional<String> identifierName,
        Set<Declaration> resolvedDeclarations
) {
    public static Resolution failure(String reason) {
        return new Resolution(
                new SemanticFailure(reason),
                Optional.empty(),
                false,
                Optional.empty(),
                Collections.emptySet()
        );
    }

    public static Resolution emptySuccess() {
        return new Resolution(
                new SemanticSuccess(),
                Optional.empty(),
                false,
                Optional.empty(),
                Collections.emptySet()
        );
    }

    public static Resolution success(Set<Declaration> newDeclarations) {
        return new Resolution(
                new SemanticSuccess(),
                Optional.empty(),
                false,
                Optional.empty(),
                newDeclarations
        );
    }
}
