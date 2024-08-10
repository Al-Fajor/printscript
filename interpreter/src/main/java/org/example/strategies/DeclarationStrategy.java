package org.example.strategies;

import org.example.ast.AstComponent;
import org.example.ast.statement.DeclarationStatement;
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
        DeclarationStatement declaration = (DeclarationStatement) astComponent;
        switch (declaration.getType()) {
            case STRING ->
                    state.addStringVariable(new Variable<>(VariableType.STRING, declaration.getName(), null));
            case NUMBER ->
                    state.addNumericVariable(new Variable<>(VariableType.NUMBER, declaration.getName(), null));
            case FUNCTION -> {}
        }
    }
}
