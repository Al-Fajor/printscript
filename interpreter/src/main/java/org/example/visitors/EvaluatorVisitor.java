package org.example.visitors;

import static org.example.ResultType.*;

import org.example.EvaluationResult;
import org.example.ResultType;
import org.example.ast.*;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.visitor.EvaluableComponentVisitor;
import org.example.function.Function;
import org.example.state.StatePriorityList;

public class EvaluatorVisitor implements EvaluableComponentVisitor<EvaluationResult> {
	private final StatePriorityList statePriorityList;

	public EvaluatorVisitor(StatePriorityList statePriorityList) {
		this.statePriorityList = statePriorityList;
	}

	@Override
	public EvaluationResult visit(BinaryExpression expression) {
		BinaryOperator operator = expression.getOperator();
		EvaluableComponent leftExpression = expression.getLeftComponent();
		EvaluableComponent rightExpression = expression.getRightComponent();
		EvaluationResult leftResult = leftExpression.accept(this);
		EvaluationResult rightResult = rightExpression.accept(this);
		return switch (operator) {
			case SUM -> addResults(leftResult, rightResult);
			case SUBTRACTION -> subtractResults(leftResult, rightResult);
			case MULTIPLICATION -> multiplyResults(leftResult, rightResult);
			case DIVISION -> divideResults(leftResult, rightResult);
			default -> throw new IllegalArgumentException("Implement the operator " + operator);
		};
	}

	@Override
	public EvaluationResult visit(Literal<?> literal) {
		switch (literal.getValue()) {
			case String s -> {
				return new EvaluationResult(s);
			}
			case Number n -> {
				return new EvaluationResult(n.doubleValue());
			}
			case Boolean b -> {
				return new EvaluationResult(b);
			}
			default -> throw new IllegalArgumentException("invalidComponent");
		}
	}

	@Override
	public EvaluationResult visit(Identifier identifier) {
		DeclarationType variableType = statePriorityList.getVariableType(identifier.getName());
		switch (variableType) {
			case NUMBER -> {
				return new EvaluationResult(getNumericValue(identifier));
			}
			case STRING -> {
				return new EvaluationResult(getStringValue(identifier));
			}
			case BOOLEAN -> {
				return new EvaluationResult(getBooleanValue(identifier));
			}
			default -> throw new IllegalArgumentException("Invalid variable type");
		}
	}

	@Override
	public EvaluationResult visit(FunctionCallStatement functionCall) {
		Function function = statePriorityList.getFunction(functionCall.getIdentifier().getName());
		Parameters parameters = functionCall.getParameters();
		return function.executeFunction(parameters);
	}

	private String getStringValue(Identifier identifier) {
		return statePriorityList.getStringVariable(identifier.getName()).getValue();
	}

	private Double getNumericValue(Identifier identifier) {
		return statePriorityList.getNumericVariable(identifier.getName()).getValue();
	}

	private Boolean getBooleanValue(Identifier identifier) {
		return statePriorityList.getBooleanVariable(identifier.getName()).getValue();
	}

	private EvaluationResult addResults(EvaluationResult leftTerm, EvaluationResult rightTerm) {
		ResultType leftTermType = leftTerm.getType();
		ResultType rightTermType = rightTerm.getType();
		if (termsAreConcatenable(leftTermType, rightTermType)) {
			String leftString = getStringResult(leftTerm);
			String rightString = getStringResult(rightTerm);
			return new EvaluationResult(leftString + rightString);
		}
		if (termsAreNumeric(leftTermType, rightTermType)) {
			Double leftNumber = leftTerm.getNumericResult();
			Double rightNumber = rightTerm.getNumericResult();
			return new EvaluationResult(leftNumber + rightNumber);
		}
		throw new IllegalArgumentException("Results cannot be added");
	}

	private EvaluationResult subtractResults(
			EvaluationResult leftTerm, EvaluationResult rightTerm) {
		ResultType leftTermType = leftTerm.getType();
		ResultType rightTermType = rightTerm.getType();
		if (termsAreNumeric(leftTermType, rightTermType)) {
			Double leftNumber = leftTerm.getNumericResult();
			Double rightNumber = rightTerm.getNumericResult();
			return new EvaluationResult(leftNumber - rightNumber);
		}
		throw new IllegalArgumentException("Results cannot be subtracted");
	}

	private EvaluationResult multiplyResults(
			EvaluationResult leftTerm, EvaluationResult rightTerm) {
		ResultType leftTermType = leftTerm.getType();
		ResultType rightTermType = rightTerm.getType();
		if (termsAreNumeric(leftTermType, rightTermType)) {
			Double leftNumber = leftTerm.getNumericResult();
			Double rightNumber = rightTerm.getNumericResult();
			return new EvaluationResult(leftNumber * rightNumber);
		}
		throw new IllegalArgumentException("Results cannot be multiplied");
	}

	private EvaluationResult divideResults(EvaluationResult leftTerm, EvaluationResult rightTerm) {
		ResultType leftTermType = leftTerm.getType();
		ResultType rightTermType = rightTerm.getType();
		if (termsAreNumeric(leftTermType, rightTermType)) {
			Double leftNumber = leftTerm.getNumericResult();
			Double rightNumber = rightTerm.getNumericResult();
			return new EvaluationResult(leftNumber / rightNumber);
		}
		throw new IllegalArgumentException("Results cannot be divided");
	}

	private String getStringResult(EvaluationResult result) {
		switch (result.getType()) {
			case STRING -> {
				return result.getStringResult();
			}
			case NUMBER -> {
				return result.getNumericResult().toString().replaceAll("\\.0\\b", "");
			}
			default -> throw new IllegalArgumentException("Result cannot be turned into string");
		}
	}

	private boolean termsAreNumeric(ResultType leftType, ResultType rightType) {
		return leftType == NUMBER && rightType == NUMBER;
	}

	private boolean termsAreConcatenable(ResultType leftType, ResultType rightType) {
		if (leftType == rightType) {
			return leftType == STRING;
		}
		if (leftType == NUMBER && rightType == STRING) {
			return true;
		}
		if (leftType == STRING && rightType == NUMBER) {
			return true;
		}
		return false;
	}
}
