package interpreter.strategies;

import interpreter.Function;
import interpreter.InterpreterState;
import interpreter.StrategySelector;
import interpreter.VariableType;
import model.*;

public class AssignationStrategy implements InterpreterStrategy{
    private final InterpreterState state;

    public AssignationStrategy(InterpreterState state) {
        this.state = state;
    }

    @Override
    public StrategyResult execute(AstComponent astComponent) {
        Assignation assignation = (Assignation) astComponent;
        switch (assignation.getLeftComponent()) {
            case Declaration d ->
                assignToNewVariable(d, assignation.getRightComponent());
            case Identifier i ->
                assignToDeclaredVariable(i, assignation.getRightComponent());
            default ->
                throw new IllegalArgumentException("Wrong node passed to Assignation");
        }
        return new StrategyResult(null, null, null);
    }

    private void assignToNewVariable(Declaration declaration, AstComponent rightComponent) {
        new DeclarationStrategy(state).execute(declaration);
        String declarationName = declaration.getName();
        DeclarationType declarationType = declaration.getType();

        StrategySelector strategySelector = new StrategySelector(state);
        switch (declarationType) {
            case STRING -> {
                String value = strategySelector.execute(rightComponent).getStringResult();
                state.setStringVariable(declarationName, value);
            }
            case NUMBER -> {
                Double value = strategySelector.execute(rightComponent).getNumericResult();
                state.setNumericVariable(declarationName, value);
            }
            case FUNCTION ->
                state.addFunction(new Function(declarationName, rightComponent));
        }
    }

    private void assignToDeclaredVariable(Identifier identifier, AstComponent rightComponent) {
        IdentifierType type = identifier.getType();
        String variableName = identifier.getName();
        if (type == IdentifierType.VARIABLE) {
            StrategySelector strategySelector = new StrategySelector(state);
            StrategyResult result = strategySelector.execute(rightComponent);
            assignValueToVariable(variableName, result);
        }
    }

    private void assignValueToVariable(String variableName, StrategyResult rightResult) {
        VariableType type = state.getVariableType(variableName);
        switch(type) {
            case STRING -> {
                String value = rightResult.getStringResult();
                state.setStringVariable(variableName, value);
            }
            case NUMBER -> {
                Double value = rightResult.getNumericResult();
                state.setNumericVariable(variableName, value);
            }
            default -> throw new IllegalArgumentException("Cannot assign value to existing function");
        }
    }
}

