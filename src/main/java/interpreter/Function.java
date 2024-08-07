package interpreter;

import model.AstComponent;
import model.Parameters;

public interface Function {
//    private final String name;
//    private final AstComponent ast;

//    public Function(String name, AstComponent ast) {
//        this.name = name;
//        this.ast = ast;
//    }

    void executeFunction(Parameters parameters);

    String getName();
}
