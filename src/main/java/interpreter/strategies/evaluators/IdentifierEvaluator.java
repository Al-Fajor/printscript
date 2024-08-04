package interpreter.strategies.evaluators;

import interpreter.InterpreterState;
import interpreter.VariableType;
import model.AstComponent;
import model.Identifier;

public class IdentifierEvaluator implements ExpressionEvaluator {
    private final InterpreterState state;

    public IdentifierEvaluator(InterpreterState state) {
        this.state = state;
    }

    @Override
    public ExpressionResult evaluate(AstComponent component) {
        Identifier identifier = (Identifier) component;
        switch(identifier.getType()) {
            case VARIABLE -> {
                return evaluateVariable(identifier);
            }
            case FUNCTION -> {
                throw new IllegalArgumentException("Implement function evaluation");
            }
            default -> {
                throw new IllegalArgumentException("invalid component");
            }
        }
    }

    private ExpressionResult evaluateVariable(Identifier identifier) {
        VariableType variableType = state.getVariableType(identifier.getName());
        switch(variableType) {
            case NUMBER -> {
                return new ExpressionResult(state.getNumericVariable(identifier.getName()).getValue());
            }
            case STRING -> {
                return new ExpressionResult(state.getStringVariable(identifier.getName()).getValue());
            }
            case BOOLEAN -> {
                throw new IllegalArgumentException("Implement Boolean variables");
            }
            default -> {
                throw new IllegalArgumentException("Invalid variable type");
            }
        }
    }
}
