package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class BinaryExpressionStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public BinaryExpressionStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public StrategyResult execute(AstComponent astComponent) {
        return null;
    }
}
