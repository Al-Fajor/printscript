package interpreter;

import model.*;

import java.util.List;

public class PrintScriptInterpreter implements Interpreter {
    private final InterpreterState state;
    private final StrategySelector strategySelector;

    public PrintScriptInterpreter() {
        state = new InterpreterState();
        strategySelector = new StrategySelector(state);
    }

    @Override
    public void interpret(List<AstComponent> astList) {
        for (AstComponent astComponent : astList) {
            strategySelector.execute(astComponent);
        }
    }
}
