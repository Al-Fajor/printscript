package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class ParametersStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public ParametersStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {

    }
}
