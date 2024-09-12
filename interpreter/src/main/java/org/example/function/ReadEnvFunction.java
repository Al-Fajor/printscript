package org.example.function;

import java.util.List;
import org.example.EvaluationResult;
import org.example.ast.DeclarationType;
import org.example.ast.EvaluableComponent;
import org.example.ast.Parameters;
import org.example.state.EnvironmentState;
import org.example.state.StatePriorityList;
import org.example.visitors.EvaluatorVisitor;

public class ReadEnvFunction implements Function {
	private final EnvironmentState envState;
	private final StatePriorityList statePriorityList;

	public ReadEnvFunction(StatePriorityList statePriorityList, EnvironmentState envState) {
		this.envState = envState;
		this.statePriorityList = statePriorityList;
	}

	@Override
	public EvaluationResult executeFunction(Parameters parameters) {
		List<EvaluableComponent> components = parameters.getParameters();

		EvaluatorVisitor evaluatorVisitor = new EvaluatorVisitor(statePriorityList);
		EvaluationResult component = components.getFirst().accept(evaluatorVisitor);
		String envVariableName = component.getStringResult();
		DeclarationType type = envState.getVariableType(envVariableName);
		if (type == null) {
			throw new RuntimeException("Variable " + envVariableName + " not found");
		}
		return switch (type) {
			case STRING ->
					new EvaluationResult(envState.getStringVariable(envVariableName).getValue());
			case NUMBER ->
					new EvaluationResult(envState.getNumericVariable(envVariableName).getValue());
			case BOOLEAN ->
					new EvaluationResult(envState.getBooleanVariable(envVariableName).getValue());
			default -> throw new RuntimeException("Unexpected value: " + type);
		};
	}

	@Override
	public String getName() {
		return "readEnv";
	}
}
