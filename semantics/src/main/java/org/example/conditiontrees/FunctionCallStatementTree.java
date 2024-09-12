package org.example.conditiontrees;

import java.util.List;
import java.util.Optional;
import org.example.Environment;
import org.example.Resolution;
import org.example.ResolvedType;
import org.example.SemanticSuccess;
import org.example.ast.IdentifierType;
import org.example.ast.Parameters;
import org.example.ast.statement.FunctionCallStatement;
import org.example.evaluables.EvaluableResolution;
import org.example.evaluables.EvaluableVisitor;

public class FunctionCallStatementTree {
	public static EvaluableResolution checkFunctionIsDeclared(
			Environment env,
			FunctionCallStatement statement,
			List<ResolvedType> types,
			String functionName) {
		if (env.isFunctionDeclared(functionName, types)) {
			return new EvaluableResolution(
					new SemanticSuccess(),
					Optional.of(env.getReturnType(functionName)),
					Optional.of(IdentifierType.FUNCTION),
					Optional.of(functionName)
			);
		} else {
			return EvaluableResolution.failure(
					"Cannot resolve function signature " + functionName + "(" + types + ").",
					statement.start(),
					statement.end());
		}
	}

	@SuppressWarnings(
			"OptionalGetWithoutIsPresent") // Safe because of firstInvalidParameterResolution
	public static List<ResolvedType> getAllParameterTypes(
			List<EvaluableResolution> resolvedParameters) {
		return resolvedParameters.stream()
				.map(resolution -> resolution.evaluatedType().get())
				.toList();
	}

	public static Optional<EvaluableResolution> getInvalidResolutionIfAny(
			List<EvaluableResolution> resolvedParameters) {
		return resolvedParameters.stream()
				.map(resolution -> getFailureIfTypeIsInvalid(resolution))
				.filter(Resolution::failed)
				.findFirst();
	}

	private static EvaluableResolution getFailureIfTypeIsInvalid(EvaluableResolution resolution) {
		if (!resolution.result().isSuccessful()) return resolution;

		// Safe.get() because resolution is successful
		@SuppressWarnings("OptionalGetWithoutIsPresent")
		ResolvedType resolvedType = resolution.evaluatedType().get();

		return switch (resolvedType) {
			// Safe .get() because failures always contain an error
			case WILDCARD, VOID -> //noinspection OptionalGetWithoutIsPresent
                    EvaluableResolution.failure(
					"Cannot pass an argument of type " + resolvedType,
					resolution.result().getErrorStart().get(),
					resolution.result().getErrorEnd().get()
			);
			default -> resolution;
		};
	}

	public static List<EvaluableResolution> resolveEachParameter(
			Parameters parameters, EvaluableVisitor evaluableVisitor) {
		return parameters.getParameters().stream()
				.map(astComponent -> astComponent.accept(evaluableVisitor))
				.toList();
	}
}
