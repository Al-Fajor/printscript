package org.example;

import java.util.HashMap;
import java.util.Map;
import org.example.ast.DeclarationType;

public class PrintScriptState implements InterpreterState {
	private final Map<String, Variable<Double>> numericVariables;
	private final Map<String, Variable<String>> stringVariables;
	private final Map<String, Variable<Boolean>> booleanVariables;
	private final Map<String, Function> functions;

	public PrintScriptState() {
		numericVariables = new HashMap<>();
		stringVariables = new HashMap<>();
		booleanVariables = new HashMap<>();
		functions = new HashMap<>();
	}

	public void addNumericVariable(Variable<Double> numericVariable) {
		numericVariables.put(numericVariable.getName(), numericVariable);
	}

	public Variable<Double> getNumericVariable(String name) {
		return numericVariables.get(name);
	}

	public void setNumericVariable(String name, Double value) {
		numericVariables.get(name).setValue(value);
	}

	public void addStringVariable(Variable<String> stringVariable) {
		stringVariables.put(stringVariable.getName(), stringVariable);
	}

	public Variable<String> getStringVariable(String name) {
		return stringVariables.get(name);
	}

	public void setStringVariable(String name, String value) {
		stringVariables.get(name).setValue(value);
	}

	public void addBooleanVariable(Variable<Boolean> variable) {
		booleanVariables.put(variable.getName(), variable);
	}

	public Variable<Boolean> getBooleanVariable(String name) {
		return booleanVariables.get(name);
	}

	public void setBooleanVariable(String name, Boolean value) {
		booleanVariables.get(name).setValue(value);
	}

	public DeclarationType getVariableType(String name) {
		if (numericVariables.containsKey(name)) return DeclarationType.NUMBER;
		if (stringVariables.containsKey(name)) return DeclarationType.STRING;
		if (booleanVariables.containsKey(name)) return DeclarationType.BOOLEAN;
		return null;
	}

	public Function getFunction(String name) {
		return functions.get(name);
	}

	public void addFunction(Function function) {
		functions.put(function.getName(), function);
	}
}
