package org.example;

import org.example.ast.Parameters;

public interface Function {
    void executeFunction(Parameters parameters);

    String getName();
}
