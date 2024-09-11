package org.example.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.Variable;
import org.example.ast.DeclarationType;

public class EnvironmentState {
	Map<String, Variable<Double>> numericVariables = new HashMap<>();
	Map<String, Variable<String>> stringVariables = new HashMap<>();
	Map<String, Variable<Boolean>> booleanVariables = new HashMap<>();

	public EnvironmentState(List<Variable<?>> envVariables) {
		insertEnvVariables(envVariables);
	}

	public Variable<Double> getNumericVariable(String name) {
		return numericVariables.get(name);
	}

	public Variable<String> getStringVariable(String name) {
		return stringVariables.get(name);
	}

	public Variable<Boolean> getBooleanVariable(String name) {
		return booleanVariables.get(name);
	}

	public DeclarationType getVariableType(String name) {
		if (numericVariables.containsKey(name)) return DeclarationType.NUMBER;
		if (stringVariables.containsKey(name)) return DeclarationType.STRING;
		if (booleanVariables.containsKey(name)) return DeclarationType.BOOLEAN;
		return null;
	}

	private void insertEnvVariables(List<Variable<?>> envVariables) {
		for (Variable<?> variable : envVariables) {
			switch (variable.getType()) {
				case NUMBER -> addNumericVariable((Variable<Double>) variable);
				case STRING -> addStringVariable((Variable<String>) variable);
				case BOOLEAN -> addBooleanVariable((Variable<Boolean>) variable);
				default ->
						throw new RuntimeException(
								"Unsupported variable type: " + variable.getType());
			}
		}
	}

	private void addNumericVariable(Variable<Double> variable) {
		numericVariables.put(variable.getName(), variable);
	}

	private void addStringVariable(Variable<String> variable) {
		stringVariables.put(variable.getName(), variable);
	}

	private void addBooleanVariable(Variable<Boolean> variable) {
		booleanVariables.put(variable.getName(), variable);
	}
}
