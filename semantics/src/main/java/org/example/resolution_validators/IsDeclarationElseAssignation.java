package org.example.resolution_validators;

import org.example.Environment;
import org.example.IdentifierResolution;

public class IsDeclarationElseAssignation extends ConditionalValidator {
    private final IdentifierResolution leftResolution;

    public IsDeclarationElseAssignation(
            IdentifierResolution leftResolution,
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        super(trueCaseValidator, falseCaseValidator);
        this.leftResolution = leftResolution;
    }

    @Override
    protected boolean meetsCondition(Environment environment) {
        return leftResolution.type().isPresent();
    }
}
