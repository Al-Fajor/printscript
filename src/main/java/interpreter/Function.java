package interpreter;

import model.AstComponent;
import model.Parameters;

public class Function {
    private final String name;
    private final AstComponent ast;

    public Function(String name, AstComponent ast) {
        this.name = name;
        this.ast = ast;
    }

    public void executeFunction(Parameters parameters) {

    }

    public String getName() {
        return name;
    }
}
