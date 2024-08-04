package interpreter.strategies.evaluators;

public class ExpressionResult {
    private final String stringResult;
    private final Double numericResult;
    private final Boolean boolResult;

    public ExpressionResult(String stringResult, Double numericResult, Boolean boolResult) {
        this.stringResult = stringResult;
        this.numericResult = numericResult;
        this.boolResult = boolResult;
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
}
