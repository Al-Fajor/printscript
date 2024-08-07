package interpreter.strategies;

import interpreter.Function;
import interpreter.InterpreterState;
import model.AstComponent;
import model.FunctionCall;
import model.Parameters;

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
