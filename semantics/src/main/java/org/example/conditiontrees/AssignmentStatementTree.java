package org.example.conditiontrees;

import org.example.Environment;
import org.example.ast.DeclarationType;
import org.example.ast.Identifier;
import org.example.ast.statement.AssignmentStatement;
import org.example.evaluables.EvaluableResolution;

public class AssignmentStatementTree {
	public static EvaluableResolution checkIdentifierExists(
			AssignmentStatement statement,
			Identifier identifier,
			EvaluableResolution assignedValueResolution,
			Environment env) {
		if (env.isVariableDeclared(identifier.getName())) {
			return checkAssigningToValidValue(statement, identifier, assignedValueResolution, env);
		} else {
			return EvaluableResolution.failure(
					"Cannot assign non-existing identifier",
					statement.getStart(),
					statement.getEnd());
		}
	}

	private static EvaluableResolution checkAssigningToValidValue(
			AssignmentStatement statement,
			Identifier identifier,
			EvaluableResolution assignedValueResolution,
			Environment env) {

		DeclarationType identifierType = env.getDeclarationType(identifier.getName());
		DeclarationType assignedValueType =
				assignedValueResolution
						.evaluatedType()
						.orElseThrow(
								() ->
										new IllegalStateException(
												"Received invalid AST: Assigning to value with no type"));

		if (identifierType == assignedValueType) {
			return EvaluableResolution.emptySuccess();
		} else {
			return EvaluableResolution.failure(
					"Cannot assign type "
							+ assignedValueResolution.evaluatedType().get()
							+ " to "
							+ env.getDeclarationType(identifier.getName()),
					statement.getStart(),
					statement.getEnd());
		}
	}
}
