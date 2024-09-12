package org.example.function;

import java.util.List;
import org.example.EvaluationResult;
import org.example.ast.EvaluableComponent;
import org.example.ast.Parameters;
import org.example.ast.visitor.EvaluableComponentVisitor;
import org.example.observer.BrokerObserver;
import org.example.state.StatePriorityList;
import org.example.visitors.EvaluatorVisitor;

public class PrintlnFunction implements Function {
	private final StatePriorityList state;
	private final BrokerObserver<String> printObserver;

	public PrintlnFunction(
			StatePriorityList statePriorityList, BrokerObserver<String> printObserver) {
		this.state = statePriorityList;
		this.printObserver = printObserver;
	}

	@Override
	public EvaluationResult executeFunction(Parameters parameters) {
		List<EvaluableComponent> components = parameters.getParameters();
		for (EvaluableComponent component : components) {
			printComponent(component);
		}
		return new EvaluationResult((String) null);
	}

	@Override
	public String getName() {
		return "println";
	}

	private void printComponent(EvaluableComponent component) {
		EvaluableComponentVisitor<EvaluationResult> evaluatorVisitor = new EvaluatorVisitor(state);
		EvaluationResult result = component.accept(evaluatorVisitor);
		printObserver.update(result.toString());
	}
}
