package interpreter;

import model.Parameters;

public interface Function {
    void executeFunction(Parameters parameters);

    String getName();
}
