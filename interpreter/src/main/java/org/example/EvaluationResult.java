package org.example;

import static org.example.ast.DeclarationType.*;

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

	public EvaluationResult(Boolean boolResult) {
		this.stringResult = null;
		this.variableType = BOOLEAN;
		this.numericResult = null;
		this.boolResult = boolResult;
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
				if (result == null) return null;

				// if number ends with .0 show as integer
				String str = result.toString();
				if (str.matches("[0-9]+(.0)$")) {
					return str.substring(0, str.length() - 2);
				} else {
					return result.toString();
				}
			}
			default -> throw new IllegalArgumentException("Implement " + getType() + " toString");
		}
	}
}
