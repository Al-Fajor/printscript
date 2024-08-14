package org.example.resolution_validators;

import org.example.Environment;
import org.example.evaluables.EvaluableResolution;
import org.example.identifiers.IdentifierResolution;

import java.util.Optional;

public class AssigningToValidValue extends ConditionalValidator {
    private final IdentifierResolution leftResolution;
    private final EvaluableResolution rightResolution;

    public AssigningToValidValue(
            IdentifierResolution leftResolution,
            EvaluableResolution rightResolution,
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        super(trueCaseValidator, falseCaseValidator);
        this.leftResolution = leftResolution;
        this.rightResolution = rightResolution;
    }

    @Override
    protected boolean meetsCondition(Environment environment) {
        return leftResolution.type().or(()-> Optional.of(environment.getDeclarationType(leftResolution.name())))
                .flatMap(value1 -> rightResolution.evaluatedType()
                .map(value2 -> value1 == value2))
                .orElseThrow(() -> new IllegalStateException(
                        "Cannot perform type comparison"
                ));
    }
}
