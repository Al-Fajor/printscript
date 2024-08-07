package interpreter;

import interpreter.strategies.evaluators.ComponentEvaluator;
import interpreter.strategies.evaluators.ExpressionResult;
import model.AstComponent;
import model.Parameters;

import java.util.List;

public class PrintlnFunction implements Function {
    private final InterpreterState state;

    public PrintlnFunction(InterpreterState state) {
        this.state = state;
    }

    @Override
    public void executeFunction(Parameters parameters) {
        List<AstComponent> components = parameters.getParameters();
        for (AstComponent component : components) {
            printComponent(component);
        }
    }

    @Override
    public String getName() {
        return "println";
    }

    private void printComponent(AstComponent component) {
        ComponentEvaluator evaluator = new ComponentEvaluator(state);
        ExpressionResult result = evaluator.evaluate(component);
        System.out.println(result);
    }
}
