package org.example.conditiontrees;

import org.example.ast.DeclarationType;
import org.example.ast.Literal;

public class LiteralTree {
	public static DeclarationType mapToDeclarationType(Literal<?> literal) {
		return switch (literal.getValue()) {
			case String ignoredString -> DeclarationType.STRING;
			case Number ignoredNumber -> DeclarationType.NUMBER;
			case Boolean ignoredNumber -> DeclarationType.BOOLEAN;
			default -> throw new IllegalStateException("Unexpected value: " + literal.getValue());
		};
	}
}
