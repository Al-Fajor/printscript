package interpreter;

import java.util.ArrayList;
import java.util.List;

public class InterpreterState {
    private List<Variable<Double>> numericVariables;
    private List<Variable<String>> stringVariables;
    private List<Function> functions;

    public InterpreterState() {
        numericVariables = new ArrayList<>();
        stringVariables = new ArrayList<>();
        functions = new ArrayList<>();
    }

    void addNumericVariable(Variable<Double> numericVariable) {
        numericVariables.add(numericVariable);
    }

    void addStringVariable(Variable<String> stringVariable) {
        stringVariables.add(stringVariable);
    }

    void addFunction(Function function) {
        functions.add(function);
    }

    VariableType getVariableType(String name) {
        return null;
    }

    Variable<Double> getNumericVariable(String name) {
        return null;
    }

    Variable<String> getStringVariable(String name) {
        return null;
    }

    Function getFunction(String name) {
        return null;
    }
}
