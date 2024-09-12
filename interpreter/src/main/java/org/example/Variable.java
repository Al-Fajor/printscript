package org.example;

import org.example.ast.DeclarationType;

public class Variable<T> {
	private final DeclarationType type;
	private final String name;
	private T value;

	public Variable(String name, T value) {
		this.type = getTypeFromValue(value);
		this.name = name;
		this.value = value;
	}

	public DeclarationType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	private DeclarationType getTypeFromValue(T value) {
		return switch (value) {
			case String ignored -> DeclarationType.STRING;
			case Double ignored -> DeclarationType.NUMBER;
			case Boolean ignored -> DeclarationType.BOOLEAN;
			default ->
					throw new IllegalStateException(
							"Unsupported variable type: " + value.getClass().getSimpleName());
		};
	}
}
