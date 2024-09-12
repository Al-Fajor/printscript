package org.example.state;

import org.example.Variable;
import org.example.ast.DeclarationType;
import org.example.function.Function;

public interface InterpreterState {
	void addNumericVariable(Variable<Double> numericVariable);

	Variable<Double> getNumericVariable(String name);

	void setNumericVariable(String name, Double value);

	void addStringVariable(Variable<String> stringVariable);

	Variable<String> getStringVariable(String name);

	void setStringVariable(String name, String value);

	void addBooleanVariable(Variable<Boolean> booleanVariable);

	Variable<Boolean> getBooleanVariable(String name);

	void setBooleanVariable(String name, Boolean value);

	DeclarationType getVariableType(String name);

	Function getFunction(String name);

	void addFunction(Function function);
}
