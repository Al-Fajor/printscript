package org.example.evaluators;

import org.example.VariableType;

public class ExpressionResult {
    private final VariableType variableType;
    private final String stringResult;
    private final Double numericResult;
    private final Boolean boolResult;

    public ExpressionResult(String stringResult) {
        this.stringResult = stringResult;
        this.variableType = VariableType.STRING;
        this.numericResult = null;
        this.boolResult = null;
    }

    public ExpressionResult(Double numericResult) {
        this.stringResult = null;
        this.variableType = VariableType.NUMBER;
        this.numericResult = numericResult;
        this.boolResult = null;
    }

    public ExpressionResult(Boolean boolResult) {
        this.stringResult = null;
        this.variableType = VariableType.BOOLEAN;
        this.numericResult = null;
        this.boolResult = boolResult;
    }

    public VariableType getType() {
        return variableType;
    }

    public String getStringResult() {
        return stringResult;
    }

    public Double getNumericResult() {
        return numericResult;
    }

    public Boolean getBoolResult() {
        return boolResult;
    }

    @Override
    public String toString() {
        switch(getType()) {
            case STRING -> {
                return getStringResult();
            }
            case NUMBER -> {
                return getNumericResult().toString();
            }
            case BOOLEAN -> {
                return getBoolResult().toString();
            }
            default -> throw new IllegalArgumentException("Implement " + getType() + " toString");
        }
    }
}