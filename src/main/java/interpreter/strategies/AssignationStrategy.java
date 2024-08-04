package interpreter.strategies;

import interpreter.InterpreterState;
import model.Assignation;
import model.AstComponent;
import model.Declaration;

public class AssignationStrategy implements InterpreterStrategy{
    private final InterpreterState state;

    public AssignationStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {
        Assignation assignation = (Assignation) astComponent;
    }
}

