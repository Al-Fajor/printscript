package org.example;

import org.example.ast.AstComponent;
import org.example.factories.RuleMapFactory;
import org.example.factories.RuleMapFactoryImpl;

import java.util.ArrayList;
import java.util.List;

public class PrintScriptFormatter implements Formatter {
    private final RuleMapFactory ruleMapFactory;

    public PrintScriptFormatter(RuleMapFactory ruleMapFactory) {
        this.ruleMapFactory = ruleMapFactory;
    }

    @Override
    public String format(List<AstComponent> asts) {
        List<String> formattedCodes = new ArrayList<>();
        FormatterVisitor visitor = new FormatterVisitor(ruleMapFactory);
        for (AstComponent ast : asts) {
            String formattedCode = ast.accept(visitor) + ";";
            formattedCodes.add(formattedCode);
        }
        return String.join("\n", formattedCodes);
    }
}
