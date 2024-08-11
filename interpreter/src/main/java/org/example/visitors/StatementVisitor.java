package org.example.visitors;

import org.example.EvaluationResult;
import org.example.InterpreterState;
import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.Visitor;

public class StatementVisitor implements Visitor<Void> {
	private final InterpreterState state;

	public StatementVisitor(InterpreterState state) {
		this.state = state;
	}

	@Override
	public Void visit(AssignationStatement statement) {
		AstComponent identifierComponent = statement.getLeft();
		Visitor<String> identifierVisitor = new IdentifierVisitor(state);
		String identifierName = identifierComponent.accept(identifierVisitor);

		AstComponent evaluableComponent = statement.getRight();
		Visitor<EvaluationResult> evaluatorVisitor = new EvaluatorVisitor(state);
		EvaluationResult result = evaluableComponent.accept(evaluatorVisitor);
		return null;
	}

	@Override
	public Void visit(IfStatement ifStatement) {
		return null;
	}

	@Override
	public Void visit(FunctionCallStatement statement) {
		return null;
	}

	@Override
	public Void visit(Declaration declaration) {
		return null;
	}

	@Override
	public Void visit(BinaryExpression expression) {
		return null;
	}

	@Override
	public Void visit(Conditional conditional) {
		return null;
	}

	@Override
	public Void visit(Literal<?> literal) {
		return null;
	}

	@Override
	public Void visit(Parameters parameters) {
		return null;
	}

	@Override
	public Void visit(StatementBlock statementBlock) {
		return null;
	}

	@Override
	public Void visit(VariableIdentifier variableIdentifier) {
		return null;
	}
}
