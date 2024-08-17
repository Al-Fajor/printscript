package org.example.ast;

public enum BinaryOperator {
    SUM,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
	OR,
	AND,
	GREATER_THAN,
	GREATER_EQUAL_THAN,
	LESS_THAN,
	LESS_EQUAL_THAN;

    public String toString() {
        return switch (this) {
            case SUM -> "+";
            case SUBTRACTION -> "-";
            case MULTIPLICATION -> "*";
            case DIVISION -> "/";
            case OR -> "|";
            case AND -> "&";
            case GREATER_THAN -> ">";
            case GREATER_EQUAL_THAN -> ">=";
            case LESS_THAN -> "<";
            case LESS_EQUAL_THAN -> "<=";
            default -> throw new IllegalArgumentException("Unexpected value: " + this);
        };
    }
}
