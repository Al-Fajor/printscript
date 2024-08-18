package org.example;

import java.util.HashMap;
import java.util.Map;

public class TestStateListener implements StateListener {
	private final Map<String, Variable<?>> variables = new HashMap<>();
	private final Map<String, Function> functions = new HashMap<>();

	@Override
	public void updateVariable(Variable<?> variable) {
		variables.put(variable.getName(), variable);
	}

	@Override
	public void updateFunction(Function function) {
		functions.put(function.getName(), function);
	}

	public Map<String, Variable<?>> getVariables() {
		return variables;
	}

	public Map<String, Function> getFunctions() {
		return functions;
	}
}
