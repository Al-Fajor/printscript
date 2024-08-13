package org.example.resolution_validators;

import org.example.Environment;
import org.example.evaluables.Resolution;

public class IsResolutionSuccessful extends ConditionalValidator {
    private final SemanticResultWrapper resolution;

    public IsResolutionSuccessful(
            SemanticResultWrapper resolution,
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        super(trueCaseValidator, falseCaseValidator);
        this.resolution = resolution;
    }

    @Override
    protected boolean meetsCondition(Environment environment) {
        return resolution.result().isSuccessful();
    }
}
