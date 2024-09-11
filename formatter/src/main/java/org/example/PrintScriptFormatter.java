package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.example.ast.AstComponent;
import org.example.ast.statement.Statement;
import org.example.factories.RuleFactory;

public class PrintScriptFormatter implements Formatter {
	private final RuleFactory ruleFactory;

	public PrintScriptFormatter(RuleFactory ruleFactory) {
		this.ruleFactory = ruleFactory;
	}

	@Override
	public String format(Iterator<Statement> asts) {
		List<String> formattedCodes = new ArrayList<>();
		FormatterVisitor visitor = new FormatterVisitor(ruleFactory);
        while (asts.hasNext()) {
            Statement ast = asts.next();
            String formattedCode = ast.accept(visitor) + ";";
            formattedCodes.add(formattedCode);
        }
		return String.join("\n", formattedCodes);
	}
}
