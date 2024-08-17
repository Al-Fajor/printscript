package org.example.visitors;

import org.example.EvaluationResult;
import org.example.Function;
import org.example.InterpreterState;
import org.example.VariableType;
import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.EvaluableComponentVisitor;
import org.example.ast.visitor.IdentifierComponentVisitor;

public class StatementVisitor implements org.example.ast.visitor.StatementVisitor<Void> {
	private final InterpreterState state;

	public StatementVisitor(InterpreterState state) {
		this.state = state;
	}

	@Override
	public Void visit(AssignationStatement statement) {
		IdentifierComponent identifierComponent = statement.getLeft();
		IdentifierComponentVisitor<String> identifierVisitor = new IdentifierVisitor(state);
		String identifierName = identifierComponent.accept(identifierVisitor);

		EvaluableComponent evaluableComponent = statement.getRight();
		EvaluableComponentVisitor<EvaluationResult> evaluatorVisitor = new EvaluatorVisitor(state);
		EvaluationResult result = evaluableComponent.accept(evaluatorVisitor);

		assignValueToIdentifier(identifierName, result);
		return null;
	}

	private void assignValueToIdentifier(String identifierName, EvaluationResult result) {
		VariableType varType = state.getVariableType(identifierName);
		switch (varType) {
			case STRING -> state.setStringVariable(identifierName, result.getStringResult());
			case NUMBER -> state.setNumericVariable(identifierName, result.getNumericResult());
			default -> throw new RuntimeException("Implement variable type: " + varType);
		}
	}

	@Override
	public Void visit(FunctionCallStatement statement) {
		Function function = getFunction(statement);
		Parameters parameters = statement.getRight();
		function.executeFunction(parameters);
		return null;
	}

	private Function getFunction(FunctionCallStatement statement) {
		IdentifierComponent identifier = statement.getLeft();
		IdentifierComponentVisitor<String> identifierVisitor = new IdentifierVisitor(state);
		String functionName = identifier.accept(identifierVisitor);
		return state.getFunction(functionName);
	}

	@Override
	public Void visit(IfStatement ifStatement) {
		return null;
	}
}
