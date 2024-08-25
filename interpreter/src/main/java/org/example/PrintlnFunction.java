package org.example;

import java.util.List;
import org.example.ast.EvaluableComponent;
import org.example.ast.Parameters;
import org.example.ast.visitor.EvaluableComponentVisitor;
import org.example.observer.BrokerObserver;
import org.example.observer.PrintBrokerObserver;
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
		EvaluableComponentVisitor<EvaluationResult> evaluatorVisitor = new EvaluatorVisitor(state);
		EvaluationResult result = component.accept(evaluatorVisitor);
		getPrintObserver().updateChanges(result.toString() + '\n');
	}

	private PrintBrokerObserver getPrintObserver() {
		List<BrokerObserver<?>> observers = state.getObservers();
		for (BrokerObserver<?> observer : observers) {
			if (observer instanceof PrintBrokerObserver) {
				return (PrintBrokerObserver) observer;
			}
		}
		throw new RuntimeException("No PrintBrokerObserver found");
	}
}
