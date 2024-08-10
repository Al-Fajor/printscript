package org.example.strategies;

import org.example.AstComponent;
import org.example.Declaration;
import org.example.InterpreterState;
import org.example.Variable;
import org.example.VariableType;

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
