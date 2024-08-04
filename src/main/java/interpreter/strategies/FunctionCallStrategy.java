package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class FunctionCallStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public FunctionCallStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {

    }
}
