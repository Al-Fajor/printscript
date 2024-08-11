package org.example.resolution_validators;

import org.example.Environment;
import org.example.Resolution;

public class IsSimpleDeclaration extends ConditionalValidator {
    private final Resolution rightResolution;

    public IsSimpleDeclaration(
            Resolution rightResolution,
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
