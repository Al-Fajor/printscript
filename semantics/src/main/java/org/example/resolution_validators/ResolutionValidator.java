package org.example.resolution_validators;

import org.example.Environment;
import org.example.Resolution;

@FunctionalInterface
public interface ResolutionValidator {
    Resolution analyze(Environment environment);
}
