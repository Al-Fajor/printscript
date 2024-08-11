package org.example.evaluables;

import org.example.SemanticFailure;
import org.example.SemanticResult;
import org.example.SemanticSuccess;
import org.example.ast.DeclarationType;

import java.util.Optional;

public record Resolution(
        SemanticResult result,
        Optional<DeclarationType> evaluatedType,
        boolean isValuePresent,
        Optional<String> identifierName
) {
    //TODO: failure should not be empty. For instance, if an identifier cannot be found,
    // we should return its name anyway, since we need it for the visit to AssignationStatement
    public static Resolution failure(String reason) {
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
