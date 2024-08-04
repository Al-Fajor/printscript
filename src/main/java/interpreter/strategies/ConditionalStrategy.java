package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class ConditionalStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public ConditionalStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {

    }
}
