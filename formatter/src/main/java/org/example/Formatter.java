package org.example;

import org.example.ast.AstComponent;

import java.util.List;

public interface Formatter {
    String format(List<AstComponent> asts);
}
