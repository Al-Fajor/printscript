package org.example;

import java.util.List;
import org.example.ast.EvaluableComponent;
import org.example.ast.Parameters;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.visitors.EvaluatorVisitor;

public class PrintlnFunction implements Function {
	private final InterpreterState state;

	public PrintlnFunction(InterpreterState state) {
		this.state = state;
	}

	@Override
	public void executeFunction(Parameters parameters) {
		List<EvaluableComponent> components = parameters.getParameters();
		for (EvaluableComponent component : components) {
			printComponent(component);
		}
	}

	@Override
	public String getName() {
		return "println";
	}

	private void printComponent(EvaluableComponent component) {
		AstComponentVisitor<EvaluationResult> evaluatorVisitor = new EvaluatorVisitor(state);
		EvaluationResult result = component.accept(evaluatorVisitor);
		System.out.println(result);
	}
}
