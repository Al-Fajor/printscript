package org.example.resolution_validators;

import org.example.Environment;
import org.example.IdentifierResolution;
import org.example.Resolution;

import java.util.Optional;

public class AssigningToValidValue extends ConditionalValidator {
    private final IdentifierResolution leftResolution;
    private final Resolution rightResolution;

    public AssigningToValidValue(
            IdentifierResolution leftResolution,
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
        return leftResolution.type().or(()-> Optional.of(environment.getDeclarationType(leftResolution.name())))
                .flatMap(value1 -> rightResolution.evaluatedType()
                .map(value2 -> value1 == value2))
                .orElseThrow(() -> new IllegalStateException(
                        "Cannot perform type comparison"
                ));
    }
}
