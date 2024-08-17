package org.example.resolution_validators;

import org.example.Environment;
import org.example.evaluables.EvaluableResolution;

@FunctionalInterface
public interface ResolutionValidator {
	EvaluableResolution analyze(Environment environment);
}
