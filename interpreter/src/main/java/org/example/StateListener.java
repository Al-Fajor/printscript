package org.example;

public interface StateListener {
	void updateVariable(Variable<?> variable);

	void updateFunction(Function function);
}
