package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.observer.Observer;

public class PrintScriptState implements InterpreterState {
	private final Map<String, Variable<Double>> numericVariables;
	private final Map<String, Variable<String>> stringVariables;
	private final Map<String, Function> functions;
	private final List<Observer<?, ?>> observers;
	private final StateListener stateListener;

	public PrintScriptState(List<Observer<?, ?>> observers, StateListener stateListener) {
		this.observers = observers;
		this.stateListener = stateListener;
		numericVariables = new HashMap<>();
		stringVariables = new HashMap<>();
		functions = new HashMap<>();
		functions.put("println", new PrintlnFunction(this));
	}

	public void addNumericVariable(Variable<Double> numericVariable) {
		numericVariables.put(numericVariable.getName(), numericVariable);
		stateListener.updateVariable(numericVariable);
	}

	public Variable<Double> getNumericVariable(String name) {
		return numericVariables.get(name);
	}

	public void setNumericVariable(String name, Double value) {
		numericVariables.get(name).setValue(value);
		stateListener.updateVariable(numericVariables.get(name));
	}

	public void addStringVariable(Variable<String> stringVariable) {
		stringVariables.put(stringVariable.getName(), stringVariable);
		stateListener.updateVariable(stringVariable);
	}

	public Variable<String> getStringVariable(String name) {
		return stringVariables.get(name);
	}

	public void setStringVariable(String name, String value) {
		stringVariables.get(name).setValue(value);
		stateListener.updateVariable(stringVariables.get(name));
	}

	public VariableType getVariableType(String name) {
		if (numericVariables.get(name) != null) {
			return VariableType.NUMBER;
		} else {
			return VariableType.STRING;
		}
	}

	public Function getFunction(String name) {
		return functions.get(name);
	}

	public void addFunction(Function function) {
		functions.put(function.getName(), function);
		stateListener.updateFunction(function);
	}

	@Override
	public List<Observer<?, ?>> getObservers() {
		return observers;
	}
}
