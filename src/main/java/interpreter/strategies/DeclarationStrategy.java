package interpreter.strategies;

import interpreter.InterpreterState;
import interpreter.Variable;
import interpreter.VariableType;
import model.*;

public class DeclarationStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public DeclarationStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {
        Declaration declaration = (Declaration) astComponent;
        switch (declaration.getType()) {
            case STRING ->
                    state.addStringVariable(new Variable<>(VariableType.STRING, declaration.getName(), null));
            case NUMBER ->
                    state.addNumericVariable(new Variable<>(VariableType.NUMBER, declaration.getName(), null));
            case FUNCTION -> {}
        }
    }
}
