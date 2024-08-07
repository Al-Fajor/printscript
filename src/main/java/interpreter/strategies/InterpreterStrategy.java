package interpreter.strategies;

import model.AstComponent;

public interface InterpreterStrategy {
    void execute(AstComponent astComponent);
}
