package org.example;

public class Variable<T> {
	private final VariableType type;
	private final String name;
	private T value;

	public Variable(VariableType type, String name, T value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public VariableType getType() {
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
