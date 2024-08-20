package org.example.identifiers;

import java.util.Optional;
import org.example.SemanticSuccess;
import org.example.ast.Declaration;
import org.example.ast.Identifier;
import org.example.ast.visitor.IdentifierComponentVisitor;

public class IdentifierVisitor implements IdentifierComponentVisitor<IdentifierResolution> {
	@Override
	public IdentifierResolution visit(Declaration statement) {
		return new IdentifierResolution(
				new SemanticSuccess(), statement.getName(), Optional.of(statement.getType()));
	}

	@Override
	public IdentifierResolution visit(Identifier identifier) {
		return new IdentifierResolution(
				new SemanticSuccess(), identifier.getName(), Optional.empty());
	}
}
