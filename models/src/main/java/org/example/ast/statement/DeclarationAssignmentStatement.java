package org.example.ast.statement;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.*;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.StatementVisitor;

public class DeclarationAssignmentStatement implements Statement {
	private final DeclarationType declarationType;
	private final IdentifierType identifierType;
	private final Identifier identifier;
	private final EvaluableComponent evaluableComponent;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public DeclarationAssignmentStatement(
			DeclarationType declarationType,
			IdentifierType identifierType,
			Identifier identifier,
			EvaluableComponent evaluableComponent,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
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

	@Override
	public int hashCode() {
		return Objects.hash(declarationType, identifierType, identifier, evaluableComponent);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof DeclarationAssignmentStatement that
				&& Objects.equals(declarationType, that.declarationType)
				&& Objects.equals(identifierType, that.identifierType)
				&& Objects.equals(identifier, that.identifier)
				&& Objects.equals(evaluableComponent, that.evaluableComponent);
	}

	@Override
	public String toString() {
		return "DeclarationAssignmentStatement{"
				+ "evaluableComponent="
				+ evaluableComponent
				+ ", identifier="
				+ identifier
				+ ", identifierType="
				+ identifierType
				+ ", declarationType="
				+ declarationType
				+ '}';
	}
}
