package org.example.visitors;

import static org.example.ast.DeclarationType.NUMBER;
import static org.example.ast.DeclarationType.STRING;

import org.example.EvaluationResult;
import org.example.InterpreterState;
import org.example.ast.*;
import org.example.ast.visitor.EvaluableComponentVisitor;

public class EvaluatorVisitor implements EvaluableComponentVisitor<EvaluationResult> {
	private final InterpreterState state;

	public EvaluatorVisitor(InterpreterState state) {
		this.state = state;
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
			case null -> {
				return new EvaluationResult((String) null);
			}
			case String s -> {
				return new EvaluationResult(s);
			}
			case Number n -> {
				return new EvaluationResult(n.doubleValue());
			}
			default -> throw new IllegalArgumentException("invalidComponent");
		}
	}

	@Override
	public EvaluationResult visit(Identifier identifier) {
		DeclarationType variableType = state.getVariableType(identifier.getName());
		switch (variableType) {
			case NUMBER -> {
				return new EvaluationResult(getNumericValue(identifier));
			}
			case STRING -> {
				return new EvaluationResult(getStringValue(identifier));
			}
			default -> throw new IllegalArgumentException("Invalid variable type");
		}
	}

	@Override
	public EvaluationResult visit(ReadInput readInput) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public EvaluationResult visit(ReadEnv readEnv) {
		throw new RuntimeException("Not implemented yet");
	}

	private String getStringValue(Identifier identifier) {
		return state.getStringVariable(identifier.getName()).getValue();
	}

	private Double getNumericValue(Identifier identifier) {
		return state.getNumericVariable(identifier.getName()).getValue();
	}

	private EvaluationResult addResults(EvaluationResult leftTerm, EvaluationResult rightTerm) {
		DeclarationType leftTermType = leftTerm.getType();
		DeclarationType rightTermType = rightTerm.getType();
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
		DeclarationType leftTermType = leftTerm.getType();
		DeclarationType rightTermType = rightTerm.getType();
		if (termsAreNumeric(leftTermType, rightTermType)) {
			Double leftNumber = leftTerm.getNumericResult();
			Double rightNumber = rightTerm.getNumericResult();
			return new EvaluationResult(leftNumber - rightNumber);
		}
		throw new IllegalArgumentException("Results cannot be subtracted");
	}

	private EvaluationResult multiplyResults(
			EvaluationResult leftTerm, EvaluationResult rightTerm) {
		DeclarationType leftTermType = leftTerm.getType();
		DeclarationType rightTermType = rightTerm.getType();
		if (termsAreNumeric(leftTermType, rightTermType)) {
			Double leftNumber = leftTerm.getNumericResult();
			Double rightNumber = rightTerm.getNumericResult();
			return new EvaluationResult(leftNumber * rightNumber);
		}
		throw new IllegalArgumentException("Results cannot be multiplied");
	}

	private EvaluationResult divideResults(EvaluationResult leftTerm, EvaluationResult rightTerm) {
		DeclarationType leftTermType = leftTerm.getType();
		DeclarationType rightTermType = rightTerm.getType();
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

	private boolean termsAreNumeric(DeclarationType leftType, DeclarationType rightType) {
		return leftType == NUMBER && rightType == NUMBER;
	}

	private boolean termsAreConcatenable(DeclarationType leftType, DeclarationType rightType) {
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
