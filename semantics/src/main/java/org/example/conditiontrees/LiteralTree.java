package org.example.conditiontrees;

import org.example.ResolvedType;
import org.example.ast.Literal;

public class LiteralTree {
	public static ResolvedType mapToDeclarationType(Literal<?> literal) {
		return switch (literal.getValue()) {
			case String ignoredString -> ResolvedType.STRING;
			case Number ignoredNumber -> ResolvedType.NUMBER;
			case Boolean ignoredNumber -> ResolvedType.BOOLEAN;
			default -> throw new IllegalStateException("Unexpected value: " + literal.getValue());
		};
	}
}
