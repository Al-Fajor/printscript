package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class LiteralStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public LiteralStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public StrategyResult execute(AstComponent astComponent) {
        return null;
    }
}
