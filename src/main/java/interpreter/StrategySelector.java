package interpreter;

import interpreter.strategies.*;
import model.*;

import java.util.HashMap;
import java.util.Map;

public class StrategySelector {
    private final InterpreterState state;
    private final Map<Class<? extends AstComponent>, InterpreterStrategy> strategyMap;

    public StrategySelector(InterpreterState state) {
        this.state = state;
        this.strategyMap = createStrategyMap();
    }

    public void execute(AstComponent astComponent) {
        strategyMap.get(astComponent.getClass()).execute(astComponent);
    }

    private Map<Class<? extends AstComponent>, InterpreterStrategy> createStrategyMap() {
        Map<Class<? extends AstComponent>, InterpreterStrategy> map = new HashMap<>();
        map.put(Assignation.class, new AssignationStrategy(state));
        map.put(IfStatement.class, new IfStatementStrategy(state));
        map.put(Parameters.class, new ParametersStrategy(state));
        map.put(Declaration.class, new DeclarationStrategy(state));
        map.put(FunctionCall.class, new FunctionCallStrategy(state));
        return map;
    }
}
