package org.example.conditiontrees;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.example.Environment;
import org.example.Resolution;
import org.example.ResolvedType;
import org.example.SemanticSuccess;
import org.example.ast.EvaluableComponent;
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
					Optional.of(functionName));
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
		return resolvedParameters.stream().filter(Resolution::failed).findFirst();
	}

	public static List<EvaluableResolution> resolveEachParameter(
			Parameters parameters, EvaluableVisitor evaluableVisitor) {
		return parameters.getParameters().stream()
				.map(acceptAndCheckNotVoid(evaluableVisitor))
				.toList();
	}

	private static Function<EvaluableComponent, EvaluableResolution> acceptAndCheckNotVoid(
			EvaluableVisitor evaluableVisitor) {
		return astComponent -> {
			EvaluableResolution resolution = astComponent.accept(evaluableVisitor);
			Optional<ResolvedType> resolvedType = resolution.evaluatedType();

			if (resolvedType.isPresent() && resolvedType.get() == ResolvedType.VOID) {
				return EvaluableResolution.failure(
						"Cannot pass as argument a void function",
						astComponent.start(),
						astComponent.end());
			}

			return resolution;
		};
	}
}
