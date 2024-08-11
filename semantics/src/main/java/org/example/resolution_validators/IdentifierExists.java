package org.example.resolution_validators;

import org.example.Environment;
import org.example.IdentifierResolution;
import org.example.Resolution;

public class IdentifierExists extends ConditionalValidator {
    private final IdentifierResolution leftResolution;

    public IdentifierExists(
            IdentifierResolution leftResolution,
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        super(trueCaseValidator, falseCaseValidator);
        this.leftResolution = leftResolution;
    }

    @Override
    protected boolean meetsCondition(Environment environment) {
        return environment.isVariableDeclared(leftResolution.name());
    }
}
