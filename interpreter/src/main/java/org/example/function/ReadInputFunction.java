package org.example.function;

import org.example.EvaluationResult;
import org.example.InputListener;
import org.example.ast.Parameters;
import org.example.observer.BrokerObserver;
import org.example.state.StatePriorityList;
import org.example.visitors.EvaluatorVisitor;

public class ReadInputFunction implements Function {
	private final InputListener inputListener;
	private final StatePriorityList statePriorityList;
	private final BrokerObserver<String> printObserver;

	public ReadInputFunction(
			InputListener inputListener,
			StatePriorityList statePriorityList,
			BrokerObserver<String> printObserver) {
		this.inputListener = inputListener;
		this.statePriorityList = statePriorityList;
		this.printObserver = printObserver;
	}

	@Override
	public EvaluationResult executeFunction(Parameters parameters) {
		String message =
				parameters
						.getParameters()
						.getFirst()
						.accept(new EvaluatorVisitor(statePriorityList))
						.getStringResult();
		printObserver.update(message);
		String input = inputListener.getInput(message);
		return new EvaluationResult((Object) input);
	}

	@Override
	public String getName() {
		return "readInput";
	}
}
