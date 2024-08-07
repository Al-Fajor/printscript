package interpreter.strategies;

import interpreter.InterpreterState;
import model.AstComponent;

public class IfStatementStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public IfStatementStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {

    }
}
