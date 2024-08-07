package interpreter.strategies;

import interpreter.InterpreterState;
import interpreter.VariableType;
import interpreter.evaluators.ComponentEvaluator;
import interpreter.evaluators.ExpressionResult;
import model.*;

public class AssignationStrategy implements InterpreterStrategy {
    private final InterpreterState state;

    public AssignationStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void execute(AstComponent astComponent) {
        Assignation assignation = (Assignation) astComponent;
        AstComponent leftComponent = assignation.getLeftComponent();
        AstComponent rightComponent = assignation.getRightComponent();
        switch (leftComponent) {
            case Declaration declaration -> assignValueToNewDeclaration(declaration, rightComponent);
            case Identifier identifier -> assignValueToExistingVariable(identifier, rightComponent);
            default -> throw new IllegalArgumentException("Wrong left component type");
        }

    }

    private void assignValueToNewDeclaration(Declaration declaration, AstComponent rightComponent) {
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

