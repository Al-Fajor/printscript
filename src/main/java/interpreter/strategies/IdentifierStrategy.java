package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class IdentifierStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public IdentifierStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {

    }
}
