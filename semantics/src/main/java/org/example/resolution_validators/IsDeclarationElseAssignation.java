package org.example.resolution_validators;

import org.example.Environment;
import org.example.Resolution;

public class IsDeclarationElseAssignation extends ConditionalValidator {
    private final Resolution leftResolution;

    public IsDeclarationElseAssignation(
            Resolution leftResolution,
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        super(trueCaseValidator, falseCaseValidator);
        this.leftResolution = leftResolution;
    }

    @Override
    protected boolean meetsCondition(Environment environment) {
        return !leftResolution.resolvedDeclarations().isEmpty();
    }
}
