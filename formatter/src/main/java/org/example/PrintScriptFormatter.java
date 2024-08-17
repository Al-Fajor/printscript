package org.example;

import org.example.ast.AstComponent;

import java.util.ArrayList;
import java.util.List;

public class PrintScriptFormatter implements Formatter {
    @Override
    public String format(List<AstComponent> asts) {
        List<String> formattedCodes = new ArrayList<>();
        FormatterVisitor visitor = new FormatterVisitor();
        for (AstComponent ast : asts) {
            String formattedCode = ast.accept(visitor) + ";";
            formattedCodes.add(formattedCode);
        }
        return String.join("\n", formattedCodes);
    }
}
