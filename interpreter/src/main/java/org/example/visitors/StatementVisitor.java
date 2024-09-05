package org.example.visitors;

import org.example.EvaluationResult;
import org.example.Function;
import org.example.InterpreterState;
import org.example.Variable;
import org.example.ast.*;
import org.example.ast.statement.*;
import org.example.ast.visitor.EvaluableComponentVisitor;

public class StatementVisitor implements org.example.ast.visitor.StatementVisitor<Void> {
	private final InterpreterState state;

	public StatementVisitor(InterpreterState state) {
		this.state = state;
	}

	@Override
	public Void visit(AssignmentStatement statement) {
		Identifier identifier = statement.getIdentifier();
		String identifierName = identifier.getName();

		EvaluableComponent evaluableComponent = statement.getEvaluableComponent();
		EvaluableComponentVisitor<EvaluationResult> evaluatorVisitor = new EvaluatorVisitor(state);
		EvaluationResult result = evaluableComponent.accept(evaluatorVisitor);

		assignValueToIdentifier(identifierName, result);
		return null;
	}

	@Override
	public Void visit(DeclarationStatement statement) {
		DeclarationType declarationType = statement.getDeclarationType();
		Identifier identifier = statement.getIdentifier();

		switch (declarationType) {
			case STRING ->
					state.addStringVariable(
							new Variable<>(declarationType, identifier.getName(), null));
			case NUMBER ->
					state.addNumericVariable(
							new Variable<>(declarationType, identifier.getName(), null));
			default ->
					throw new RuntimeException("Unexpected declaration type: " + declarationType);
		}
		return null;
	}

	@Override
	public Void visit(DeclarationAssignmentStatement statement) {
		DeclarationType declarationType = statement.getDeclarationType();
		IdentifierType identifierType = statement.getIdentifierType();
		Identifier identifier = statement.getIdentifier();

		DeclarationStatement declarationStatement =
				new DeclarationStatement(
						declarationType,
						identifierType,
						identifier,
						statement.getStart(),
						statement.getEnd());
		this.visit(declarationStatement);

		EvaluableComponent evaluableComponent = statement.getEvaluableComponent();
		AssignmentStatement assignmentStatement =
				new AssignmentStatement(
						identifier, evaluableComponent, statement.getStart(), statement.getEnd());
		this.visit(assignmentStatement);
		return null;
	}

	private void assignValueToIdentifier(String identifierName, EvaluationResult result) {
		DeclarationType varType = state.getVariableType(identifierName);
		switch (varType) {
			case STRING -> state.setStringVariable(identifierName, result.getStringResult());
			case NUMBER -> state.setNumericVariable(identifierName, result.getNumericResult());
			default -> throw new RuntimeException("Implement variable type: " + varType);
		}
	}

	@Override
	public Void visit(FunctionCallStatement statement) {
		Function function = getFunction(statement);
		Parameters parameters = statement.getParameters();
		function.executeFunction(parameters);
		return null;
	}

	private Function getFunction(FunctionCallStatement statement) {
		Identifier identifier = statement.getIdentifier();
		String functionName = identifier.getName();
		return state.getFunction(functionName);
	}

	@Override
	public Void visit(IfStatement ifStatement) {
		return null;
	}
}
