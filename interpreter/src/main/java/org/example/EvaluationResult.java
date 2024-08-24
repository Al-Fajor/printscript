package org.example;

public class EvaluationResult {
	private final VariableType variableType;
	private final String stringResult;
	private final Double numericResult;
	private final Boolean boolResult;

	public EvaluationResult(String stringResult) {
		this.stringResult = stringResult;
		this.variableType = VariableType.STRING;
		this.numericResult = null;
		this.boolResult = null;
	}

	public EvaluationResult(Double numericResult) {
		this.stringResult = null;
		this.variableType = VariableType.NUMBER;
		this.numericResult = numericResult;
		this.boolResult = null;
	}

	public EvaluationResult(Boolean boolResult) {
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
		switch (getType()) {
			case STRING -> {
				return getStringResult();
			}
			case NUMBER -> {
				Double result = getNumericResult();
				return result != null ? result.toString() : null;
			}
			case BOOLEAN -> {
				Boolean result = getBoolResult();
				return result != null ? getBoolResult().toString() : null;
			}
			default -> throw new IllegalArgumentException("Implement " + getType() + " toString");
		}
	}
}
