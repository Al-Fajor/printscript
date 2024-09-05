package org.example.ast.statement;

import org.example.Pair;
import org.example.ast.DeclarationType;
import org.example.ast.Identifier;
import org.example.ast.IdentifierType;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.StatementVisitor;

public class DeclarationStatement implements Statement {
	private DeclarationType declarationType;
	private IdentifierType identifierType;
	private Identifier identifier;
	private Pair<Integer, Integer> start;
	private Pair<Integer, Integer> end;

	public DeclarationStatement(
			DeclarationType declarationType, IdentifierType identifierType, Identifier identifier) {
		this.declarationType = declarationType;
		this.identifierType = identifierType;
		this.identifier = identifier;
	}

	public DeclarationType getDeclarationType() {
		return declarationType;
	}

	public IdentifierType getIdentifierType() {
		return identifierType;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public Pair<Integer, Integer> getStart() {
		return start;
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		return end;
	}

	@Override
	public <T> T accept(AstComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <T> T accept(StatementVisitor<T> visitor) {
		return visitor.visit(this);
	}
}