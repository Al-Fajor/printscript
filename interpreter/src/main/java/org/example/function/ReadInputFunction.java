package org.example.function;

import org.example.EvaluationResult;
import org.example.InputListener;
import org.example.ast.Parameters;
import org.example.state.StatePriorityList;
import org.example.visitors.EvaluatorVisitor;

public class ReadInputFunction implements Function {
	private final InputListener inputListener;
	private final StatePriorityList statePriorityList;

	public ReadInputFunction(InputListener inputListener, StatePriorityList statePriorityList) {
		this.inputListener = inputListener;
		this.statePriorityList = statePriorityList;
	}

	@Override
	public EvaluationResult executeFunction(Parameters parameters) {
		String message =
				parameters
						.getParameters()
						.getFirst()
						.accept(new EvaluatorVisitor(statePriorityList))
						.getStringResult();
		String input = inputListener.getInput(message);
		return new EvaluationResult((Object) input);
	}

	@Override
	public String getName() {
		return "readInput";
	}
}
