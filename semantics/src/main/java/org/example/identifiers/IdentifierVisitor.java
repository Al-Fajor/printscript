package org.example.identifiers;

import java.util.Optional;
import org.example.SemanticSuccess;
import org.example.ast.BinaryExpression;
import org.example.ast.Conditional;
import org.example.ast.Declaration;
import org.example.ast.Identifier;
import org.example.ast.Literal;
import org.example.ast.Parameters;
import org.example.ast.StatementBlock;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.Visitor;

public class IdentifierVisitor implements Visitor<IdentifierResolution> {
	@Override
	public IdentifierResolution visit(BinaryExpression expression) {
		return null;
	}

	@Override
	public IdentifierResolution visit(Conditional conditional) {
		return null;
	}

	@Override
	public IdentifierResolution visit(IfStatement ifStatement) {
		return null;
	}

	@Override
	public IdentifierResolution visit(Literal<?> literal) {
		return null;
	}

	@Override
	public IdentifierResolution visit(Parameters parameters) {
		return null;
	}

	@Override
	public IdentifierResolution visit(AssignationStatement statement) {
		return null;
	}

	@Override
	public IdentifierResolution visit(Declaration statement) {
		return new IdentifierResolution(
				new SemanticSuccess(), statement.getName(), Optional.of(statement.getType()));
	}

	@Override
	public IdentifierResolution visit(FunctionCallStatement statement) {
		return null;
	}

	@Override
	public IdentifierResolution visit(StatementBlock statementBlock) {
		return null;
	}

	@Override
	public IdentifierResolution visit(Identifier identifier) {
		return new IdentifierResolution(
				new SemanticSuccess(), identifier.getName(), Optional.empty());
	}
}
