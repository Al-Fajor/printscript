package interpreter;

import model.*;

import java.util.List;

public class PrintScriptInterpreter implements Interpreter {
    private final InterpreterState state;
    private final StrategySelector strategySelector;

    public PrintScriptInterpreter() {
        state = new PrintScriptState();
        strategySelector = new StrategySelector(state);
    }

    public PrintScriptInterpreter(InterpreterState state) {
        this.state = state;
        this.strategySelector = new StrategySelector(state);
    }

    @Override
    public void interpret(List<AstComponent> astList) {
        for (AstComponent astComponent : astList) {
            strategySelector.execute(astComponent);
        }
    }
}
