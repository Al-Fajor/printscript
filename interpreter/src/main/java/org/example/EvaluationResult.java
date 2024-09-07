package org.example;

import static org.example.ast.DeclarationType.NUMBER;
import static org.example.ast.DeclarationType.STRING;

import org.example.ast.DeclarationType;

public class EvaluationResult {
	private final DeclarationType variableType;
	private final String stringResult;
	private final Double numericResult;
	private final Boolean boolResult;

	public EvaluationResult(String stringResult) {
		this.stringResult = stringResult;
		this.variableType = STRING;
		this.numericResult = null;
		this.boolResult = null;
	}

	public EvaluationResult(Double numericResult) {
		this.stringResult = null;
		this.variableType = NUMBER;
		this.numericResult = numericResult;
		this.boolResult = null;
	}

	public DeclarationType getType() {
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
			default -> throw new IllegalArgumentException("Implement " + getType() + " toString");
		}
	}
}
