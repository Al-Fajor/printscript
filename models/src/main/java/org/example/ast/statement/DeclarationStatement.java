package org.example.ast.statement;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.DeclarationType;
import org.example.ast.Identifier;
import org.example.ast.IdentifierType;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.StatementVisitor;

public class DeclarationStatement implements Statement {
	private final DeclarationType declarationType;
	private final IdentifierType identifierType;
	private final Identifier identifier;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public DeclarationStatement(
			DeclarationType declarationType,
			IdentifierType identifierType,
			Identifier identifier,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
		this.declarationType = declarationType;
		this.identifierType = identifierType;
		this.identifier = identifier;
		this.start = start;
		this.end = end;
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
	public Pair<Integer, Integer> start() {
		return start;
	}

	@Override
	public Pair<Integer, Integer> end() {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DeclarationStatement that)) return false;
		return declarationType == that.declarationType
				&& identifierType == that.identifierType
				&& Objects.equals(identifier, that.identifier);
	}

	@Override
	public int hashCode() {
		return Objects.hash(declarationType, identifierType, identifier);
	}
}
