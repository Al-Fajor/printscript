package interpreter;

import model.*;

import java.util.List;

public class PrintScriptInterpreter implements Interpreter {
    private List<Variable> variables;
    @Override
    public void interpret(List<AstComponent> astList) {
        for (AstComponent astComponent : astList) {
            interpretTree(astComponent);
        }
    }

    private void interpretTree(AstComponent astComponent) {
        switch(astComponent) {
            case Assignation a -> {
                a.getChildren();
            }
            default -> {
                throw new IllegalArgumentException("Implement");
            }
        }
    }
}
