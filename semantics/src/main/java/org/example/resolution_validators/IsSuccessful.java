package org.example.resolution_validators;

import org.example.Environment;
import org.example.evaluables.Resolution;

public class IsSuccessful extends ConditionalValidator {
    private final Resolution resolution;

    public IsSuccessful(
            Resolution resolution,
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
