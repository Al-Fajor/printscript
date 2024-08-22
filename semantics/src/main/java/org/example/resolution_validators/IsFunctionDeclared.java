package org.example.resolution_validators;

import java.util.List;
import org.example.Environment;
import org.example.ast.DeclarationType;
import org.example.identifiers.IdentifierResolution;

public class IsFunctionDeclared extends ConditionalValidator {
	private final List<DeclarationType> resolvedTypes;
	private final IdentifierResolution functionCallResolution;

	public IsFunctionDeclared(
			List<DeclarationType> resolvedTypes,
			IdentifierResolution functionCallResolution,
			ResolutionValidator trueCaseValidator,
			ResolutionValidator falseCaseValidator) {
		super(trueCaseValidator, falseCaseValidator);
		this.resolvedTypes = resolvedTypes;
		this.functionCallResolution = functionCallResolution;
	}

	@Override
	protected boolean meetsCondition(Environment environment) {
		String functionName = functionCallResolution.name();

		return environment.isFunctionDeclared(functionName, resolvedTypes);
	}
}
