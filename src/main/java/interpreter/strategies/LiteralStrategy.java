package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class LiteralStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public LiteralStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {

    }
}
