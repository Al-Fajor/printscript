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
					"Variable has already been declared", statement.getStart(), statement.getEnd());
		} else {
			@SuppressWarnings(
					"OptionalGetWithoutIsPresent") // Safe because declarations always have a type
			DeclarationType leftType = identifierResolution.type().get();
			DeclarationType rightType = assignedValueResolution.evaluatedType().get();
			String name = identifierResolution.name();

			return checkDeclaringWithValidValue(statement, assignedValueResolution);
		}
	}

	private static EvaluableResolution checkDeclaringWithValidValue(
			DeclarationAssignmentStatement statement, EvaluableResolution assignedValueResolution) {

		if (statement.getDeclarationType() == assignedValueResolution.evaluatedType()) {
			return new EvaluableResolution(SUCCESS, Optional.of(identifierType), Optional.of(name));
		} else {
			return EvaluableResolution.failure(
					"Cannot assign value of type "
							+ assignedType
							+ " to variable of type "
							+ identifierType,
					statement.getStart(),
					statement.getEnd());
		}
	}
}
