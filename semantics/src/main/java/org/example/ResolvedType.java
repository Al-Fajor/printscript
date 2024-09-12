package org.example;

import java.util.Optional;
import org.example.ast.DeclarationType;

public enum ResolvedType {
	STRING("string"),
	NUMBER("number"),
	BOOLEAN("boolean"),
	WILDCARD("wildcard"),
	VOID("void");

	private final String stringified;

	ResolvedType(String stringified) {
		this.stringified = stringified;
	}

	@Override
	public String toString() {
		return stringified;
	}

	public static ResolvedType from(DeclarationType declarationType) {
		return switch (declarationType) {
			case NUMBER -> ResolvedType.NUMBER;
			case STRING -> ResolvedType.STRING;
			case BOOLEAN -> ResolvedType.BOOLEAN;
			case FUNCTION -> throw new IllegalStateException("Cannot map function type");
		};
	}

	public static Optional<DeclarationType> asDeclarationType(ResolvedType evaluatedType) {
		return switch (evaluatedType) {
			case STRING -> Optional.of(DeclarationType.STRING);
			case NUMBER -> Optional.of(DeclarationType.NUMBER);
			case BOOLEAN -> Optional.of(DeclarationType.BOOLEAN);
			case WILDCARD, VOID -> Optional.empty();
		};
	}
}
