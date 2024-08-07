package interpreter;

import model.AstComponent;

import java.util.List;

public interface Interpreter {
    void interpret(List<AstComponent> astList);
}
