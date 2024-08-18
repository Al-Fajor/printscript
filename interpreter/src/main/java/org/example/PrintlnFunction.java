package org.example;

import java.util.List;
import org.example.ast.EvaluableComponent;
import org.example.ast.Parameters;
import org.example.ast.visitor.EvaluableComponentVisitor;
import org.example.observer.Observer;
import org.example.observer.PrintObserver;
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

	private PrintObserver getPrintObserver() {
		List<Observer<?, ?>> observers = state.getObservers();
		for (Observer<?, ?> observer : observers) {
			if (observer instanceof PrintObserver) {
				return (PrintObserver) observer;
			}
		}
		throw new RuntimeException("No PrintObserver found");
	}
}
