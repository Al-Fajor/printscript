package org.example.conditiontrees;

import org.example.Environment;
import org.example.ResolvedType;
import org.example.ast.DeclarationType;
import org.example.ast.Identifier;
import org.example.ast.IdentifierType;
import org.example.ast.statement.AssignmentStatement;
import org.example.evaluables.EvaluableResolution;

public class AssignmentStatementTree {
	public static EvaluableResolution checkIdentifierExists(
			AssignmentStatement statement,
			Identifier identifier,
			EvaluableResolution assignedValueResolution,
			Environment env) {
		if (env.isVariableDeclared(identifier.getName())) {
			return checkNotConstant(statement, identifier, assignedValueResolution, env);
		} else {
			return EvaluableResolution.failure(
					"Cannot assign non-existing identifier", statement.start(), statement.end());
		}
	}

	private static EvaluableResolution checkNotConstant(
			AssignmentStatement statement,
			Identifier identifier,
			EvaluableResolution assignedValueResolution,
			Environment env) {
		boolean isConst = env.getIdentifierType(identifier.getName()) == IdentifierType.CONST;
		if (isConst) {
			return EvaluableResolution.failure(
					"Cannot reassign constant", statement.start(), statement.end());
		} else {
			return checkAssigningToValidValue(statement, identifier, assignedValueResolution, env);
		}
	}

	private static EvaluableResolution checkAssigningToValidValue(
			AssignmentStatement statement,
			Identifier identifier,
			EvaluableResolution assignedValueResolution,
			Environment env) {

		DeclarationType variableType = env.getVariableDeclarationType(identifier.getName());
		ResolvedType identifierType = ResolvedType.from(variableType);
		ResolvedType assignedValueType =
				assignedValueResolution
						.evaluatedType()
						.orElseThrow(
								() ->
										new IllegalStateException(
												"Received invalid AST: Assigning to value with no declarationType"));

		if (identifierType == assignedValueType) {
			return EvaluableResolution.emptySuccess();
		} else {
			return EvaluableResolution.failure(
					"Cannot assign declarationType "
							+ assignedValueResolution.evaluatedType().get()
							+ " to "
							+ env.getVariableDeclarationType(identifier.getName()),
					statement.start(),
					statement.end());
		}
	}
}
