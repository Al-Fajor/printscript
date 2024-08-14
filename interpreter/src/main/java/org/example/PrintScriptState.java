package org.example;

import java.util.HashMap;
import java.util.Map;

public class PrintScriptState implements InterpreterState {
    private final Map<String, Variable<Double>> numericVariables;
    private final Map<String, Variable<String>> stringVariables;
    private final Map<String, Function> functions;

    public PrintScriptState() {
        numericVariables = new HashMap<>();
        stringVariables = new HashMap<>();
        functions = new HashMap<>();
        functions.put("println", new PrintlnFunction(this));
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
    }
}
