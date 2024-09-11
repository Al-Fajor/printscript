package org.example.visitors;

import org.example.*;
import org.example.ast.*;
import org.example.ast.statement.*;
import org.example.ast.visitor.EvaluableComponentVisitor;

public class StatementVisitor implements org.example.ast.visitor.StatementVisitor<Void> {
	private final StatePriorityList statePriorityList;

	public StatementVisitor(StatePriorityList statePriorityList) {
		this.statePriorityList = statePriorityList;
	}

	@Override
	public Void visit(AssignmentStatement statement) {
		Identifier identifier = statement.getIdentifier();
		String identifierName = identifier.getName();

		EvaluableComponent evaluableComponent = statement.getEvaluableComponent();
		EvaluableComponentVisitor<EvaluationResult> evaluatorVisitor =
				new EvaluatorVisitor(statePriorityList);
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
					statePriorityList.addStringVariable(
							new Variable<>(declarationType, identifier.getName(), null));
			case NUMBER ->
					statePriorityList.addNumericVariable(
							new Variable<>(declarationType, identifier.getName(), null));
			case BOOLEAN ->
					statePriorityList.addBooleanVariable(
							new Variable<>(declarationType, identifier.getName(), null));
			case FUNCTION -> throw new RuntimeException("Function declaration not implemented");
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
						statement.start(),
						statement.end());
		this.visit(declarationStatement);

		EvaluableComponent evaluableComponent = statement.getEvaluableComponent();
		AssignmentStatement assignmentStatement =
				new AssignmentStatement(
						identifier, evaluableComponent, statement.start(), statement.end());
		this.visit(assignmentStatement);
		return null;
	}

	private void assignValueToIdentifier(String identifierName, EvaluationResult result) {
		DeclarationType varType = statePriorityList.getVariableType(identifierName);
		switch (varType) {
			case STRING ->
					statePriorityList.setStringVariable(identifierName, result.getStringResult());
			case NUMBER ->
					statePriorityList.setNumericVariable(identifierName, result.getNumericResult());
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

	@Override
	public Void visit(IfStatement ifStatement) {
		EvaluatorVisitor evaluatorVisitor = new EvaluatorVisitor(statePriorityList);
		EvaluationResult conditionResult =
				ifStatement.conditionalIdentifier().accept(evaluatorVisitor);
		Boolean condition = conditionResult.getBoolResult();
		if (condition) {
			statePriorityList.addState(new PrintScriptState());
			for (Statement statement : ifStatement.trueClause()) {
				statement.accept(this);
			}
		}
		return null;
	}

	private Function getFunction(FunctionCallStatement statement) {
		Identifier identifier = statement.getIdentifier();
		String functionName = identifier.getName();
		return statePriorityList.getFunction(functionName);
	}

	@Override
	public Void visit(IfElseStatement ifElseStatement) {
		EvaluatorVisitor evaluatorVisitor = new EvaluatorVisitor(statePriorityList);
		EvaluationResult conditionResult =
				ifElseStatement.conditionalIdentifier().accept(evaluatorVisitor);
		Boolean condition = conditionResult.getBoolResult();

		statePriorityList.addState(new PrintScriptState());
		if (condition) {
			for (Statement statement : ifElseStatement.trueClause()) {
				statement.accept(this);
			}
		} else {
			for (Statement statement : ifElseStatement.falseClause()) {
				statement.accept(this);
			}
		}
		statePriorityList.popState();
		return null;
	}
}
