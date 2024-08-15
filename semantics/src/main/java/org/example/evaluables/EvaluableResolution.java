package org.example.evaluables;

import org.example.Pair;
import org.example.SemanticFailure;
import org.example.SemanticResult;
import org.example.SemanticSuccess;
import org.example.ast.DeclarationType;
import org.example.Resolution;

import java.util.Optional;

public record EvaluableResolution(
        SemanticResult result,
        Optional<DeclarationType> evaluatedType,
        boolean isValuePresent,
        Optional<String> identifierName
) implements Resolution {

    public static final String BAD_USAGE_ERROR_MESSAGE = "EvaluableResolution.failure(Resolution) must be called with a failed Resolution";

    public static EvaluableResolution failure(
            String reason,
            Pair<Integer, Integer> start,
            Pair<Integer, Integer> end

    ) {
        return new EvaluableResolution(
                new SemanticFailure(reason, Optional.of(start), Optional.of(end)),
                Optional.empty(),
                false,
                Optional.empty()
        );
    }

    public static EvaluableResolution castFrom(Resolution otherResolution) {
        // If I extract the throwable, the compiler does not know it's an unchecked exception
        // and starts crying over it.
        return EvaluableResolution.failure(
                otherResolution.result().errorMessage(),
                otherResolution.result().getErrorStart().orElseThrow(
                        () -> new IllegalArgumentException(BAD_USAGE_ERROR_MESSAGE)
                ),
                otherResolution.result().getErrorEnd().orElseThrow(
                        () -> new IllegalArgumentException(BAD_USAGE_ERROR_MESSAGE)
                )
        );
    }

    public static EvaluableResolution emptySuccess() {
        return new EvaluableResolution(
                new SemanticSuccess(),
                Optional.empty(),
                false,
                Optional.empty()
        );
    }
}
