package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class IdentifierStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public IdentifierStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public StrategyResult execute(AstComponent astComponent) {
        return null;
    }
}
