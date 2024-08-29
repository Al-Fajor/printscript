package org.example.conditiontrees;

import static org.example.evaluables.EvaluableVisitor.SUCCESS;

import java.util.Optional;
import org.example.Environment;
import org.example.SemanticFailure;
import org.example.ast.Identifier;
import org.example.evaluables.EvaluableResolution;

public class IdentifierTree {
	public static EvaluableResolution existingIdentifier(Environment env, Identifier identifier) {
		return new EvaluableResolution(
				SUCCESS,
				Optional.of(env.getDeclarationType(identifier.getName())),
				Optional.of(identifier.getName()));
	}

	public static EvaluableResolution identifierNotFound(Identifier identifier) {
		return new EvaluableResolution(
				new SemanticFailure(
						"Cannot find identifier " + identifier.getName(),
						Optional.of(identifier.getStart()),
						Optional.of(identifier.getEnd())),
				Optional.empty(),
				Optional.of(identifier.getName()));
	}
}
