package org.example;

import org.example.function.Function;

public interface StateListener {
	void updateVariable(Variable<?> variable);

	void updateFunction(Function function);
}
