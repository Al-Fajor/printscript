package org.example.resolution_validators;

import java.util.List;
import org.example.Environment;
import org.example.ast.DeclarationType;
import org.example.identifiers.IdentifierResolution;
import org.example.parameters.ParametersResolution;

public class IsFunctionDeclared extends ConditionalValidator {
	private ParametersResolution parameterResolution;
	private IdentifierResolution functionCallResolution;

	public IsFunctionDeclared(
			ParametersResolution parameterResolution,
			IdentifierResolution functionCallResolution,
			ResolutionValidator trueCaseValidator,
			ResolutionValidator falseCaseValidator) {
		super(trueCaseValidator, falseCaseValidator);
		this.parameterResolution = parameterResolution;
		this.functionCallResolution = functionCallResolution;
	}

	@Override
	protected boolean meetsCondition(Environment environment) {
		List<DeclarationType> types = parameterResolution.types();
		String functionName = functionCallResolution.name();

		return environment.isFunctionDeclared(functionName, types);
	}
}
