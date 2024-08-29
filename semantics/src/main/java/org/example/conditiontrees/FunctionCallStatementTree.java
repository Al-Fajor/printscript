package org.example.conditiontrees;

import java.util.List;
import java.util.Optional;
import org.example.Environment;
import org.example.Resolution;
import org.example.ast.DeclarationType;
import org.example.ast.Parameters;
import org.example.ast.statement.FunctionCallStatement;
import org.example.evaluables.EvaluableResolution;
import org.example.evaluables.EvaluableVisitor;

public class FunctionCallStatementTree {
	public static EvaluableResolution isFunctionDeclared(
			Environment env,
			FunctionCallStatement statement,
			List<DeclarationType> types,
			String functionName) {
		if (env.isFunctionDeclared(functionName, types)) {
			return EvaluableResolution.emptySuccess();
		} else {
			return EvaluableResolution.failure(
					"Cannot resolve function signature " + functionName + "(" + types + ").",
					statement.getStart(),
					statement.getEnd());
		}
	}

	@SuppressWarnings(
			"OptionalGetWithoutIsPresent") // Safe because of firstInvalidParameterResolution
	public static List<DeclarationType> getAllParameterTypes(
			List<EvaluableResolution> resolvedParameters) {
		return resolvedParameters.stream()
				.map(resolution -> resolution.evaluatedType().get())
				.toList();
	}

	public static Optional<EvaluableResolution> getInvalidResolutionIfAny(
			List<EvaluableResolution> resolvedParameters) {
		return resolvedParameters.stream().filter(Resolution::failed).findFirst();
	}

	public static List<EvaluableResolution> resolveEachParameter(
			Parameters parameters, EvaluableVisitor evaluableVisitor) {
		return parameters.getParameters().stream()
				.map(astComponent -> astComponent.accept(evaluableVisitor))
				.toList();
	}
}
