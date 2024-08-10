package org.example;

import org.example.ast.AstComponent;
import org.example.ast.Parameters;
import org.example.evaluators.ComponentEvaluator;
import org.example.evaluators.ExpressionResult;

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
