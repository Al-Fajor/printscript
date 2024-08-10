package org.example.evaluators;

import org.example.AstComponent;
import org.example.BinaryExpression;
import org.example.InterpreterState;
import org.example.VariableType;

import static org.example.VariableType.NUMBER;
import static org.example.VariableType.STRING;

public class BinaryExpressionEvaluator implements ExpressionEvaluator {
    private final InterpreterState state;

    public BinaryExpressionEvaluator(InterpreterState state) {
        this.state = state;
    }

    @Override
    public ExpressionResult evaluate(AstComponent component) {
        checkValidComponent(component);
        BinaryExpression binaryExpression = (BinaryExpression) component;
        switch (binaryExpression.getOperator()) {
            case SUM -> {
                ExpressionResult leftResult = getResult(binaryExpression.getLeftComponent());
                ExpressionResult rightResult = getResult(binaryExpression.getRightComponent());
                return sumResults(leftResult, rightResult);
            }
            case SUBTRACTION -> {
                ExpressionResult leftResult = getResult(binaryExpression.getLeftComponent());
                ExpressionResult rightResult = getResult(binaryExpression.getRightComponent());
                return subtractResults(leftResult, rightResult);
            }
            case MULTIPLICATION -> {
                ExpressionResult leftResult = getResult(binaryExpression.getLeftComponent());
                ExpressionResult rightResult = getResult(binaryExpression.getRightComponent());
                return multiplyResults(leftResult, rightResult);
            }
            case DIVISION -> {
                ExpressionResult leftResult = getResult(binaryExpression.getLeftComponent());
                ExpressionResult rightResult = getResult(binaryExpression.getRightComponent());
                return divideResults(leftResult, rightResult);
            }
            default -> throw new IllegalArgumentException("Implement " + binaryExpression.getOperator() + " evaluation");
        }
    }

    private ExpressionResult getResult(AstComponent component) {
        return new ComponentEvaluator(state).evaluate(component);
    }

    private ExpressionResult sumResults(ExpressionResult leftTerm, ExpressionResult rightTerm) {
        VariableType leftTermType = leftTerm.getType();
        VariableType rightTermType = rightTerm.getType();
        if (termsAreConcatenable(leftTermType, rightTermType)) {
            String leftString = getStringResult(leftTerm);
            String rightString = getStringResult(rightTerm);
            return new ExpressionResult(leftString + rightString);
        }
        if (termsAreNumeric(leftTermType, rightTermType)) {
            Double leftNumber = leftTerm.getNumericResult();
            Double rightNumber = rightTerm.getNumericResult();
            return new ExpressionResult(leftNumber + rightNumber);
        }
        throw new IllegalArgumentException("Results cannot be summed");
    }

    private ExpressionResult subtractResults(ExpressionResult leftTerm, ExpressionResult rightTerm) {
        VariableType leftTermType = leftTerm.getType();
        VariableType rightTermType = rightTerm.getType();
        if (termsAreNumeric(leftTermType, rightTermType)) {
            Double leftNumber = leftTerm.getNumericResult();
            Double rightNumber = rightTerm.getNumericResult();
            return new ExpressionResult(leftNumber - rightNumber);
        }
        throw new IllegalArgumentException("Results cannot be subtracted");
    }
    private ExpressionResult multiplyResults(ExpressionResult leftTerm, ExpressionResult rightTerm) {
        VariableType leftTermType = leftTerm.getType();
        VariableType rightTermType = rightTerm.getType();
        if (termsAreNumeric(leftTermType, rightTermType)) {
            Double leftNumber = leftTerm.getNumericResult();
            Double rightNumber = rightTerm.getNumericResult();
            return new ExpressionResult(leftNumber * rightNumber);
        }
        throw new IllegalArgumentException("Results cannot be multiplied");
    }

    private ExpressionResult divideResults(ExpressionResult leftTerm, ExpressionResult rightTerm) {
        VariableType leftTermType = leftTerm.getType();
        VariableType rightTermType = rightTerm.getType();
        if (termsAreNumeric(leftTermType, rightTermType)) {
            Double leftNumber = leftTerm.getNumericResult();
            Double rightNumber = rightTerm.getNumericResult();
            return new ExpressionResult(leftNumber / rightNumber);
        }
        throw new IllegalArgumentException("Results cannot be divided");
    }


    private String getStringResult(ExpressionResult result) {
        switch(result.getType()) {
            case STRING -> {return result.getStringResult();}
            case NUMBER -> {return result.getNumericResult().toString();}
            case BOOLEAN -> {return result.getBoolResult().toString();}
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

    private void checkValidComponent(AstComponent component) {
        if (!(component instanceof BinaryExpression)) {
            throw new IllegalArgumentException("Component must be a BinaryExpression");
        }
    }
}
