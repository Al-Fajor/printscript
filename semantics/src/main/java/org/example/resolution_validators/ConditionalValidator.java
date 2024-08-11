package org.example.resolution_validators;

import org.example.Environment;
import org.example.evaluables.Resolution;

// The Template Method pattern will be more readable than Strategy, in this case
public abstract class ConditionalValidator implements ResolutionValidator {
    ResolutionValidator trueCaseValidator;
    ResolutionValidator falseCaseValidator;

    public ConditionalValidator(
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        this.trueCaseValidator = trueCaseValidator;
        this.falseCaseValidator = falseCaseValidator;
    }

    @Override
    public Resolution analyze(Environment environment) {
        if (meetsCondition(environment)) {
            return trueCaseValidator.analyze(environment);
        }
        else return falseCaseValidator.analyze(environment);
    }

    protected abstract boolean meetsCondition(Environment environment);
}
