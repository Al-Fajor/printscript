package org.example.conditiontrees;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.example.Environment;
import org.example.ast.DeclarationType;
import org.example.ast.Identifier;
import org.example.ast.statement.Statement;
import org.example.evaluables.EvaluableResolution;
import org.example.evaluables.EvaluableVisitor;

public class IfStatementTree {
	public static EvaluableResolution checkIsBooleanIdentifier(
			Identifier identifier, Environment env) {
		if (env.getVariableDeclarationType(identifier.getName()) == DeclarationType.BOOLEAN) {
			return EvaluableResolution.emptySuccess();
		} else {
			return EvaluableResolution.failure(
					"The conditional of an if statement must be a boolean variable",
					identifier.start(),
					identifier.end());
		}
	}

	public static Stream<EvaluableResolution> resolveInnerStatements(
			EvaluableVisitor evaluableVisitor, Iterable<Statement> clause) {
		return StreamSupport.stream(clause.spliterator(), false)
				.map(statement -> statement.accept(evaluableVisitor))
				.filter(resolution -> !resolution.result().isSuccessful());
	}
}
