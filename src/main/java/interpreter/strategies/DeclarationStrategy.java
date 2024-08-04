package interpreter.strategies;

import interpreter.InterpreterState;
import model.*;

public class DeclarationStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public DeclarationStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {
        Declaration declaration = (Declaration) astComponent;
    }
}
