package org.example.resolution_validators;

import org.example.Environment;
import org.example.Resolution;

public class IsResolutionSuccessful extends ConditionalValidator {
	private final Resolution resolution;

	public IsResolutionSuccessful(
			Resolution resolution,
			ResolutionValidator trueCaseValidator,
			ResolutionValidator falseCaseValidator) {
		super(trueCaseValidator, falseCaseValidator);
		this.resolution = resolution;
	}

	@Override
	protected boolean meetsCondition(Environment environment) {
		return resolution.result().isSuccessful();
	}
}
