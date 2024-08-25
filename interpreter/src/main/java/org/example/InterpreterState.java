package org.example;

import java.util.List;
import org.example.observer.BrokerObserver;

public interface InterpreterState {
	void addNumericVariable(Variable<Double> numericVariable);

	Variable<Double> getNumericVariable(String name);

	void setNumericVariable(String name, Double value);

	void addStringVariable(Variable<String> stringVariable);

	Variable<String> getStringVariable(String name);

	void setStringVariable(String name, String value);

	VariableType getVariableType(String name);

	Function getFunction(String name);

	void addFunction(Function function);

	List<BrokerObserver<?>> getObservers();
}
