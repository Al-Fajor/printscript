package org.example.resolution_validators;

import org.example.Environment;
import org.example.evaluables.EvaluableResolution;

public class IsSimpleDeclaration extends ConditionalValidator {
    private final EvaluableResolution rightResolution;

    public IsSimpleDeclaration(
            EvaluableResolution rightResolution,
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        super(trueCaseValidator, falseCaseValidator);
        this.rightResolution = rightResolution;
    }

    @Override
    protected boolean meetsCondition(Environment environment) {
        return !rightResolution.isValuePresent();
    }
}
