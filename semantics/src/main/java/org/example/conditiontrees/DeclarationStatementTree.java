package org.example.conditiontrees;

import static org.example.evaluables.EvaluableVisitor.SUCCESS;

import java.util.Optional;
import org.example.Environment;
import org.example.ast.statement.DeclarationStatement;
import org.example.evaluables.EvaluableResolution;

public class DeclarationStatementTree {
	public static EvaluableResolution checkIdentifierDoesNotExist(
			DeclarationStatement statement, Environment env) {
		String name = statement.getIdentifier().getName();
		if (env.isVariableDeclared(name)) {
			return EvaluableResolution.failure(
					"Variable has already been declared", statement.start(), statement.end());
		} else {
			return new EvaluableResolution(
					SUCCESS,
					Optional.of(statement.getDeclarationType()),
					Optional.of(statement.getIdentifierType()),
					Optional.of(name));
		}
	}
}
