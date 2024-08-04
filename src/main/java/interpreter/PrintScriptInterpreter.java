package interpreter;

import model.*;

import java.util.List;

public class PrintScriptInterpreter implements Interpreter {
    private InterpreterState state;

    public PrintScriptInterpreter() {
        state = new InterpreterState();
    }

    @Override
    public void interpret(List<AstComponent> astList) {
        for (AstComponent astComponent : astList) {
            interpretTree(astComponent);
        }
    }

    private void interpretTree(AstComponent astComponent) {

    }
}
