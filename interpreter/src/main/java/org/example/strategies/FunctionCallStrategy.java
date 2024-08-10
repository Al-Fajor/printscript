package org.example.strategies;

import org.example.Function;
import org.example.FunctionCall;
import org.example.InterpreterState;
import org.example.AstComponent;
import org.example.Parameters;

public class FunctionCallStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public FunctionCallStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {
        FunctionCall functionCall = (FunctionCall) astComponent;
        Function function = state.getFunction(functionCall.getIdentifier().getName());
        Parameters parameters = functionCall.getParameters();
        function.executeFunction(parameters);
    }
}
