package interpreter.strategies;

import interpreter.Function;
import interpreter.InterpreterState;
import interpreter.StrategySelector;
import model.*;

public class AssignationStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public AssignationStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {
        Assignation assignation = (Assignation) astComponent;

    }
}

