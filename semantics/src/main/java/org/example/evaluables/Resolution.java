package org.example.evaluables;

import org.example.SemanticFailure;
import org.example.SemanticResult;
import org.example.SemanticSuccess;
import org.example.ast.DeclarationType;
import org.example.resolution_validators.SemanticResultWrapper;

import java.util.Optional;

public record Resolution(
        SemanticResult result,
        Optional<DeclarationType> evaluatedType,
        boolean isValuePresent,
        Optional<String> identifierName
) implements SemanticResultWrapper {
    public static Resolution emptyFailure(String reason) {
        return new Resolution(
                new SemanticFailure(reason),
                Optional.empty(),
                false,
                Optional.empty()
        );
    }

    public static Resolution emptySuccess() {
        return new Resolution(
                new SemanticSuccess(),
                Optional.empty(),
                false,
                Optional.empty()
        );
    }
}
