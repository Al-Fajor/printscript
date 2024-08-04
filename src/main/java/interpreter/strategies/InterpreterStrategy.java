package interpreter.strategies;

import model.AstComponent;

public interface InterpreterStrategy {
    StrategyResult execute(AstComponent astComponent);
}
