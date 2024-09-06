package org.example;

import org.example.ast.DeclarationType;

public class Variable<T> {
	private final DeclarationType type;
	private final String name;
	private T value;

	public Variable(DeclarationType type, String name, T value) {
		this.type = type;
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
}
