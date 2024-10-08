package org.example.conditiontrees;

import static org.example.evaluables.EvaluableVisitor.SUCCESS;

import java.util.Optional;
import org.example.Environment;
import org.example.ResolvedType;
import org.example.ast.statement.DeclarationAssignmentStatement;
import org.example.evaluables.EvaluableResolution;

public class DeclarationAssignmentStatementTree {
	public static EvaluableResolution checkIdentifierDoesNotExist(
			DeclarationAssignmentStatement statement,
			EvaluableResolution assignedValueResolution,
			Environment env) {
		if (env.isVariableDeclared(statement.getIdentifier().getName())) {
			return EvaluableResolution.failure(
					"Variable has already been declared", statement.start(), statement.end());
		} else {
			return checkDeclaringWithValidValue(statement, assignedValueResolution);
		}
	}

	private static EvaluableResolution checkDeclaringWithValidValue(
			DeclarationAssignmentStatement statement, EvaluableResolution assignedValueResolution) {

		ResolvedType identifierType = ResolvedType.from(statement.getDeclarationType());
		Optional<ResolvedType> assignedType = assignedValueResolution.evaluatedType();

		if (typesMatch(identifierType, assignedType)) {
			String name = statement.getIdentifier().getName();
			return new EvaluableResolution(
					SUCCESS,
					Optional.of(identifierType),
					Optional.of(statement.getIdentifierType()),
					Optional.of(name));
		} else {
			return EvaluableResolution.failure(
					"Cannot assign value of declarationType "
							+ assignedType
							+ " to variable of declarationType "
							+ identifierType,
					statement.start(),
					statement.end());
		}
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	// Need to pass the optional because it's necessary for the boolean logic
	private static boolean typesMatch(
			ResolvedType identifierType, Optional<ResolvedType> assignedType) {
		// e.g. declarationType is determined at runtime for readEnv
		boolean isRuntimeType = assignedType.get() == ResolvedType.WILDCARD;
		return isRuntimeType || identifierType == assignedType.get();
	}
}
