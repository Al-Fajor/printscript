package org.example;

import java.util.LinkedList;
import org.example.ast.DeclarationType;

public class StatePriorityList {
	private final LinkedList<InterpreterState> states;

	public StatePriorityList() {
		states = new LinkedList<>();
	}

	public StatePriorityList(LinkedList<InterpreterState> states) {
		this.states = states;
	}

	/**
	 * Sets state object as the higher priority state.
	 *
	 * @param state Added State.
	 */
	public void addState(InterpreterState state) {
		states.addFirst(state);
	}

	/** Removes the highest priority state. */
	public void popState() {
		states.removeFirst();
	}

	/**
	 * Adds new numeric variable to the highest priority state.
	 *
	 * @param variable Added variable.
	 */
	public void addNumericVariable(Variable<Double> variable) {
		states.getFirst().addNumericVariable(variable);
	}

	/**
	 * Looks for and returns the variable whose name matches the input, starts looking from the
	 * highest priority.
	 *
	 * @param name The variable name.
	 * @return Variable if found, null if not found.
	 */
	public Variable<Double> getNumericVariable(String name) {
		for (InterpreterState state : states) {
			Variable<Double> variable = state.getNumericVariable(name);
			if (variable != null) {
				return variable;
			}
		}
		return null;
	}

	/**
	 * Sets a value to a variable, taking state priority into account.
	 *
	 * @param name The variable name.
	 * @param value The value to be assigned.
	 */
	public void setNumericVariable(String name, Double value) {
		this.getNumericVariable(name).setValue(value);
	}

	/**
	 * Adds new numeric variable to the highest priority state.
	 *
	 * @param variable Added variable.
	 */
	public void addStringVariable(Variable<String> variable) {
		states.getFirst().addStringVariable(variable);
	}

	/**
	 * Looks for and returns the variable whose name matches the input, starts looking from the
	 * highest priority.
	 *
	 * @param name The variable name.
	 * @return Variable if found, null if not found.
	 */
	public Variable<String> getStringVariable(String name) {
		for (InterpreterState state : states) {
			Variable<String> variable = state.getStringVariable(name);
			if (variable != null) {
				return variable;
			}
		}
		return null;
	}

	/**
	 * Sets a value to a variable, taking state priority into account.
	 *
	 * @param name The variable name.
	 * @param value The value to be assigned.
	 */
	public void setStringVariable(String name, String value) {
		this.getStringVariable(name).setValue(value);
	}

	/**
	 * Adds new numeric variable to the highest priority state.
	 *
	 * @param variable Added variable.
	 */
	public void addBooleanVariable(Variable<Boolean> variable) {
		states.getFirst().addBooleanVariable(variable);
	}

	/**
	 * Looks for and returns the variable whose name matches the input, starts looking from the
	 * highest priority.
	 *
	 * @param name The variable name.
	 * @return Variable if found, null if not found.
	 */
	public Variable<Boolean> getBooleanVariable(String name) {
		for (InterpreterState state : states) {
			Variable<Boolean> variable = state.getBooleanVariable(name);
			if (variable != null) {
				return variable;
			}
		}
		return null;
	}

	/**
	 * Sets a value to a variable, taking state priority into account.
	 *
	 * @param name The variable name.
	 * @param value The value to be assigned.
	 */
	public void setBooleanVariable(String name, Boolean value) {
		this.getBooleanVariable(name).setValue(value);
	}

	/**
	 * Returns the DeclarationType of a variable taking state priority into account.
	 *
	 * @param name The variable name.
	 * @return DeclarationType if found, throws RuntimeException if not found.
	 */
	public DeclarationType getVariableType(String name) {
		Variable<Double> numericVariable = this.getNumericVariable(name);
		if (numericVariable != null) return numericVariable.getType();

		Variable<String> stringVariable = this.getStringVariable(name);
		if (stringVariable != null) return stringVariable.getType();

		Variable<Boolean> booleanVariable = this.getBooleanVariable(name);
		if (booleanVariable != null) return booleanVariable.getType();

		throw new RuntimeException("Variable " + name + " not found");
	}

	public Function getFunction(String name) {
		for (InterpreterState state : states) {
			Function function = state.getFunction(name);
			if (function != null) return function;
		}
		return null;
	}

	public void addFunction(Function function) {
		states.getFirst().addFunction(function);
	}
}
