package org.example;

import org.example.ast.AstComponent;

import java.util.List;

public class PrintScriptFormatter implements Formatter {
    @Override
    public String format(List<AstComponent> asts) {
        FormatterVisitor visitor = new FormatterVisitor();
        return asts.stream()
            .map(ast -> ast.accept(visitor))
            .reduce("", (acc, ast) -> acc + ast + "\n");
    }
}
