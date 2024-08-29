package org.example.conditiontrees;

import static org.example.evaluables.EvaluableVisitor.SUCCESS;

import java.util.Optional;
import org.example.Environment;
import org.example.ast.DeclarationType;
import org.example.ast.statement.AssignationStatement;
import org.example.evaluables.EvaluableResolution;
import org.example.identifiers.IdentifierResolution;

public class AssignationStatementTree {
	public static EvaluableResolution validateDeclarationOrAssignation(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution,
			Environment env) {
		if (isDeclaration(identifierResolution)) {
			return checkIdentifierDoesNotExist(
					statement, identifierResolution, assignedValueResolution, env);
		} else {
			return checkIdentifierExists(
					statement, identifierResolution, assignedValueResolution, env);
		}
	}

	private static boolean isDeclaration(IdentifierResolution identifierResolution) {
		return identifierResolution.type().isPresent();
	}

	private static EvaluableResolution checkIdentifierExists(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution,
			Environment env) {
		if (env.isVariableDeclared(identifierResolution.name())) {
			return checkAssigningToValidValue(
					statement, identifierResolution, assignedValueResolution, env);
		} else {
			return EvaluableResolution.failure(
					"Cannot assign non-existing identifier",
					statement.getStart(),
					statement.getEnd());
		}
	}

	private static EvaluableResolution checkAssigningToValidValue(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution,
			Environment env) {

		DeclarationType identifierType = env.getDeclarationType(identifierResolution.name());
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
							+ env.getDeclarationType(identifierResolution.name()),
					statement.getStart(),
					statement.getEnd());
		}
	}

	private static EvaluableResolution checkIdentifierDoesNotExist(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution,
			Environment env) {
		if (env.isVariableDeclared(identifierResolution.name())) {
			return EvaluableResolution.failure(
					"Variable has already been declared", statement.getStart(), statement.getEnd());
		} else {
			return checkIsSimpleDeclaration(
					statement, identifierResolution, assignedValueResolution);
		}
	}

	private static EvaluableResolution checkIsSimpleDeclaration(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution) {
		if (isSimpleDeclaration(assignedValueResolution)) {
			return new EvaluableResolution(
					SUCCESS, identifierResolution.type(), Optional.of(identifierResolution.name()));
		} else {

			@SuppressWarnings(
					"OptionalGetWithoutIsPresent") // Safe because declarations always have a type
			DeclarationType leftType = identifierResolution.type().get();
			DeclarationType rightType = assignedValueResolution.evaluatedType().get();
			String name = identifierResolution.name();

			return checkDeclaringWithValidValue(statement, leftType, rightType, name);
		}
	}

	private static boolean isSimpleDeclaration(EvaluableResolution assignedValueResolution) {
		return assignedValueResolution.evaluatedType().isEmpty();
	}

	private static EvaluableResolution checkDeclaringWithValidValue(
			AssignationStatement statement,
			DeclarationType identifierType,
			DeclarationType assignedType,
			String name) {

		if (identifierType == assignedType) {
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
