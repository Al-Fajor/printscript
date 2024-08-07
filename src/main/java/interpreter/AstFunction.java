package interpreter;

import model.*;

public class AstFunction implements Function {
    private final String name;
    private final AstComponent ast;

    public AstFunction(String name, AstComponent ast) {
        this.name = name;
        this.ast = ast;
    }

    @Override
    public void executeFunction(Parameters parameters) {
        // TODO implement
    }

    @Override
    public String getName() {
        return name;
    }
}
