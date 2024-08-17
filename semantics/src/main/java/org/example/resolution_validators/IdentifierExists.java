package org.example.resolution_validators;

import org.example.Environment;
import org.example.ast.Identifier;
import org.example.identifiers.IdentifierResolution;

public class IdentifierExists extends ConditionalValidator {
	private final String name;

	public IdentifierExists(
			IdentifierResolution resolution,
			ResolutionValidator trueCaseValidator,
			ResolutionValidator falseCaseValidator) {
		super(trueCaseValidator, falseCaseValidator);
		this.name = resolution.name();
	}

	public IdentifierExists(
			Identifier identifier,
			ResolutionValidator trueCaseValidator,
			ResolutionValidator falseCaseValidator) {
		super(trueCaseValidator, falseCaseValidator);
		this.name = identifier.getName();
	}

	@Override
	protected boolean meetsCondition(Environment environment) {
		return environment.isVariableDeclared(name);
	}
}
