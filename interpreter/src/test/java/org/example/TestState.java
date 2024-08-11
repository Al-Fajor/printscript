package org.example;

public class TestState implements InterpreterState {
    private final InterpreterState state = new PrintScriptState();

    public void addNumericVariable(Variable<Double> numericVariable) {
        state.addNumericVariable(numericVariable);
    }

    public Variable<Double> getNumericVariable(String name) {
        return state.getNumericVariable(name);
    }

    public void setNumericVariable(String name, Double value) {
        state.setNumericVariable(name, value);
    }

    public void addStringVariable(Variable<String> stringVariable) {
        state.addStringVariable(stringVariable);
    }

    public Variable<String> getStringVariable(String name) {
        return state.getStringVariable(name);
    }

    public void setStringVariable(String name, String value) {
        state.setStringVariable(name, value);
    }

    public VariableType getVariableType(String name) {
        return state.getVariableType(name);
    }

    public Function getFunction(String name) {
        return state.getFunction(name);
    }

    public void addFunction(Function function) {
        state.addFunction(function);
    }
}
