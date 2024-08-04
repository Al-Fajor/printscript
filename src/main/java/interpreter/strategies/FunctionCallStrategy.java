package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class FunctionCallStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public FunctionCallStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public StrategyResult execute(AstComponent astComponent) {
        return null;
    }
}
