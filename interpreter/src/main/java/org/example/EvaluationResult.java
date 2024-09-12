package org.example;

import static org.example.ResultType.*;

public class EvaluationResult {
	private final ResultType resultType;
	private final String stringResult;
	private final Double numericResult;
	private final Boolean boolResult;
	private final Object undefinedResult;

	public EvaluationResult(String stringResult) {
		this.stringResult = stringResult;
		this.undefinedResult = null;
		this.resultType = STRING;
		this.numericResult = null;
		this.boolResult = null;
	}

	public EvaluationResult(Double numericResult) {
		this.undefinedResult = null;
		this.stringResult = null;
		this.resultType = NUMBER;
		this.numericResult = numericResult;
		this.boolResult = null;
	}

	public EvaluationResult(Boolean boolResult) {
		this.undefinedResult = null;
		this.stringResult = null;
		this.resultType = BOOLEAN;
		this.numericResult = null;
		this.boolResult = boolResult;
	}

	public EvaluationResult(Object undefinedTypeResult) {
		this.undefinedResult = undefinedTypeResult;
		this.stringResult = null;
		this.resultType = UNDEFINED;
		this.numericResult = null;
		this.boolResult = null;
	}

	public ResultType getType() {
		return resultType;
	}

	public String getStringResult() {
		if (resultType == UNDEFINED) {
			return undefinedResult.toString();
		}
		return stringResult;
	}

	public Double getNumericResult() {
		if (resultType == UNDEFINED) {
			return Double.parseDouble(undefinedResult.toString());
		}
		return numericResult;
	}

	public Boolean getBoolResult() {
		if (resultType == UNDEFINED) {
			return Boolean.getBoolean(undefinedResult.toString());
		}
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
			case BOOLEAN -> {
				Boolean result = getBoolResult();
				return result == null ? null : result.toString();
			}
			case UNDEFINED -> {
				return undefinedResult.toString();
			}
			default -> throw new IllegalArgumentException("Implement " + getType() + " toString");
		}
	}
}
