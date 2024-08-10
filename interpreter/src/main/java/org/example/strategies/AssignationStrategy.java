package org.example.strategies;

import org.example.ast.statement.AssignationStatement;
import org.example.ast.AstComponent;
import org.example.ast.statement.DeclarationStatement;
import org.example.ast.IdentifierType;
import org.example.InterpreterState;
import org.example.VariableType;
import org.example.evaluators.ComponentEvaluator;
import org.example.evaluators.ExpressionResult;

public class AssignationStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public AssignationStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {
        AssignationStatement assignation = (AssignationStatement) astComponent;
        AstComponent leftComponent = assignation.getLeftComponent();
        AstComponent rightComponent = assignation.getRightComponent();
        switch (leftComponent) {
            case DeclarationStatement declaration -> assignValueToNewDeclaration(declaration, rightComponent);
            case Identifier identifier -> assignValueToExistingVariable(identifier, rightComponent);
            default -> throw new IllegalArgumentException("Wrong left component type");
        }

    }

    private void assignValueToNewDeclaration(DeclarationStatement declaration, AstComponent rightComponent) {
        new DeclarationStrategy(state).execute(declaration);
        switch (declaration.getType()) {
            case STRING -> {
                ExpressionResult rightComponentResult = new ComponentEvaluator(state).evaluate(rightComponent);
                state.setStringVariable(declaration.getName(), rightComponentResult.getStringResult());
            }
            case NUMBER -> {
                ExpressionResult rightComponentResult = new ComponentEvaluator(state).evaluate(rightComponent);
                state.setNumericVariable(declaration.getName(), rightComponentResult.getNumericResult());
            }
            case FUNCTION -> throw new RuntimeException("Implement function declaration");
        }
    }

    private void assignValueToExistingVariable(Identifier identifier, AstComponent rightComponent) {
        if (identifier.getType() == IdentifierType.VARIABLE) {
            ExpressionResult rightComponentResult = new ComponentEvaluator(state).evaluate(rightComponent);
            String variableName = identifier.getName();
            assignResultToVariable(variableName, rightComponentResult);
        }
    }

    private void assignResultToVariable(String variableName, ExpressionResult rightComponentResult) {
        VariableType varType = state.getVariableType(variableName);
        switch (varType) {
            case STRING -> state.setStringVariable(variableName, rightComponentResult.getStringResult());
            case NUMBER -> state.setNumericVariable(variableName, rightComponentResult.getNumericResult());
            default -> throw new RuntimeException("Implement variable type: " + varType);
        }
    }
}

