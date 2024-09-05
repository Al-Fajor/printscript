package org.example.ast.statement;

import org.example.Pair;
import org.example.ast.*;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.StatementVisitor;

public class DeclarationAssignmentStatement implements Statement {
	private DeclarationType declarationType;
	private IdentifierType variableType;
	private Identifier identifier;
	private EvaluableComponent evaluableComponent;
	private Pair<Integer, Integer> start;
	private Pair<Integer, Integer> end;

	public DeclarationAssignmentStatement(
			DeclarationType declarationType,
			IdentifierType variableType,
			Identifier identifier,
			EvaluableComponent evaluableComponent) {
		this.declarationType = declarationType;
		this.variableType = variableType;
		this.identifier = identifier;
		this.evaluableComponent = evaluableComponent;
	}

	public DeclarationType getDeclarationType() {
		return declarationType;
	}

	public IdentifierType getVariableType() {
		return variableType;
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
