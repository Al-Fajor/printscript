package interpreter;

import model.AstComponent;
import model.Parameters;

public class Function {
    private final String name;
    private final AstComponent ast;
    private InterpreterState outerState;
    private FunctionState innerState;

    public Function(String name, AstComponent ast, InterpreterState outerState) {
        this.name = name;
        this.ast = ast;
        this.outerState = outerState;
    }

    public void executeFunction(Parameters parameters) {

    }

    public FunctionState getInnerState() {
        return innerState;
    }
}
