package org.example.resolution_validators;

import org.example.Environment;
import org.example.Resolution;

public class IdentifierExists extends ConditionalValidator {
    private final Resolution leftResolution;

    public IdentifierExists(
            Resolution leftResolution,
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        super(trueCaseValidator, falseCaseValidator);
        this.leftResolution = leftResolution;
    }

    @Override
    protected boolean meetsCondition(Environment environment) {
        return leftResolution.identifierName()
                .map(environment::isVariableDeclared)
                .orElse(false);
    }
}
