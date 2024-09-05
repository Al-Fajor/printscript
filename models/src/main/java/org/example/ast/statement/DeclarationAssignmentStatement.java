package org.example.ast.statement;

import org.example.Pair;
import org.example.ast.*;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.StatementVisitor;

public class DeclarationAssignmentStatement implements Statement {
	private DeclarationType declarationType;
	private IdentifierType identifierType;
	private Identifier identifier;
	private EvaluableComponent evaluableComponent;
	private Pair<Integer, Integer> start;
	private Pair<Integer, Integer> end;

	public DeclarationAssignmentStatement(
            DeclarationType declarationType,
            IdentifierType identifierType,
            Identifier identifier,
            EvaluableComponent evaluableComponent, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
		this.declarationType = declarationType;
		this.identifierType = identifierType;
		this.identifier = identifier;
		this.evaluableComponent = evaluableComponent;
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

	public EvaluableComponent getEvaluableComponent() {
		return evaluableComponent;
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
	public Pair<Integer, Integer> getStart() {
		return start;
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		return end;
	}
}
