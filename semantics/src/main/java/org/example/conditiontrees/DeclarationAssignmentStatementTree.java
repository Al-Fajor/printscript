package org.example.conditiontrees;

import static org.example.evaluables.EvaluableVisitor.SUCCESS;

import java.util.Optional;
import org.example.Environment;
import org.example.ast.DeclarationType;
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

	@SuppressWarnings(
			"OptionalGetWithoutIsPresent") // Safe because Resolution is successful at this point
	private static EvaluableResolution checkDeclaringWithValidValue(
			DeclarationAssignmentStatement statement, EvaluableResolution assignedValueResolution) {

		DeclarationType identifierType = statement.getDeclarationType();
		DeclarationType assignedType = assignedValueResolution.evaluatedType().get();
		if (identifierType == assignedType) {
			String name = statement.getIdentifier().getName();
			return new EvaluableResolution(SUCCESS, Optional.of(identifierType), Optional.of(name));
		} else {
			return EvaluableResolution.failure(
					"Cannot assign value of type "
							+ assignedType
							+ " to variable of type "
							+ identifierType,
					statement.start(),
					statement.end());
		}
	}
}
