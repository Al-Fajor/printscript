package org.example.resolution_validators;

import org.example.Environment;
import org.example.Resolution;

public class AssigningToValidValue extends ConditionalValidator {
    private final Resolution leftResolution;
    private final Resolution rightResolution;

    public AssigningToValidValue(
            Resolution leftResolution,
            Resolution rightResolution,
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        super(trueCaseValidator, falseCaseValidator);
        this.leftResolution = leftResolution;
        this.rightResolution = rightResolution;
    }

    @Override
    protected boolean meetsCondition(Environment environment) {
        return leftResolution.evaluatedType()
                .flatMap(value1 -> rightResolution.evaluatedType()
                .map(value2 -> value1 == value2))
                .orElse(false);
    }
}
