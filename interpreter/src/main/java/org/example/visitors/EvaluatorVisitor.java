package org.example.visitors;

import static org.example.VariableType.NUMBER;
import static org.example.VariableType.STRING;

import org.example.EvaluationResult;
import org.example.InterpreterState;
import org.example.VariableType;
import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.AstComponentVisitor;

public class EvaluatorVisitor implements AstComponentVisitor<EvaluationResult> {
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
		if (identifier.getType() == IdentifierType.VARIABLE) {
			VariableType variableType = state.getVariableType(identifier.getName());
			switch (variableType) {
				case NUMBER -> {
					return new EvaluationResult(getNumericValue(identifier));
				}
				case STRING -> {
					return new EvaluationResult(getStringValue(identifier));
				}
				case BOOLEAN ->
						throw new UnsupportedOperationException("Implement Boolean variables");
				default -> throw new IllegalArgumentException("Invalid variable type");
			}
		} else {
			throw new UnsupportedOperationException("Implement function evaluation");
		}
	}

	@Override
	public EvaluationResult visit(Parameters parameters) {
		throw new UnsupportedOperationException("Implement parameters evaluation");
	}

	@Override
	public EvaluationResult visit(Conditional conditional) {
		throw new UnsupportedOperationException("Implement Conditional variables");
	}

	@Override
	public EvaluationResult visit(IfStatement ifStatement) {
		throw new UnsupportedOperationException("Implement IfStatement variables");
	}

	private String getStringValue(Identifier identifier) {
		return state.getStringVariable(identifier.getName()).getValue();
	}

	private Double getNumericValue(Identifier identifier) {
		return state.getNumericVariable(identifier.getName()).getValue();
	}

	private EvaluationResult addResults(EvaluationResult leftTerm, EvaluationResult rightTerm) {
		VariableType leftTermType = leftTerm.getType();
		VariableType rightTermType = rightTerm.getType();
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
		VariableType leftTermType = leftTerm.getType();
		VariableType rightTermType = rightTerm.getType();
		if (termsAreNumeric(leftTermType, rightTermType)) {
			Double leftNumber = leftTerm.getNumericResult();
			Double rightNumber = rightTerm.getNumericResult();
			return new EvaluationResult(leftNumber - rightNumber);
		}
		throw new IllegalArgumentException("Results cannot be subtracted");
	}

	private EvaluationResult multiplyResults(
			EvaluationResult leftTerm, EvaluationResult rightTerm) {
		VariableType leftTermType = leftTerm.getType();
		VariableType rightTermType = rightTerm.getType();
		if (termsAreNumeric(leftTermType, rightTermType)) {
			Double leftNumber = leftTerm.getNumericResult();
			Double rightNumber = rightTerm.getNumericResult();
			return new EvaluationResult(leftNumber * rightNumber);
		}
		throw new IllegalArgumentException("Results cannot be multiplied");
	}

	private EvaluationResult divideResults(EvaluationResult leftTerm, EvaluationResult rightTerm) {
		VariableType leftTermType = leftTerm.getType();
		VariableType rightTermType = rightTerm.getType();
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
				return result.getNumericResult().toString();
			}
			case BOOLEAN -> {
				return result.getBoolResult().toString();
			}
			default -> throw new IllegalArgumentException("Result cannot be turned into string");
		}
	}

	private boolean termsAreNumeric(VariableType leftType, VariableType rightType) {
		return leftType == NUMBER && rightType == NUMBER;
	}

	private boolean termsAreConcatenable(VariableType leftType, VariableType rightType) {
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

	@Override
	public EvaluationResult visit(AssignationStatement statement) {
		return null;
	}

	@Override
	public EvaluationResult visit(Declaration statement) {
		return null;
	}

	@Override
	public EvaluationResult visit(FunctionCallStatement statement) {
		return null;
	}

	@Override
	public EvaluationResult visit(StatementBlock statementBlock) {
		return null;
	}
}
