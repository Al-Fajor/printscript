package org.example.strategies;

import org.example.Function;
import org.example.ast.statement.FunctionCallStatement;
import org.example.InterpreterState;
import org.example.ast.AstComponent;
import org.example.ast.Parameters;

public class FunctionCallStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public FunctionCallStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {
        FunctionCallStatement functionCall = (FunctionCallStatement) astComponent;
        Function function = state.getFunction(functionCall.getIdentifier().getName());
        Parameters parameters = functionCall.getParameters();
        function.executeFunction(parameters);
    }
}
