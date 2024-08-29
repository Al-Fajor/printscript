package org.example;

import java.util.ArrayList;
import java.util.List;
import org.example.ast.AstComponent;
import org.example.factories.RuleFactory;

public class PrintScriptFormatter implements Formatter {
	private final RuleFactory ruleFactory;

	public PrintScriptFormatter(RuleFactory ruleFactory) {
		this.ruleFactory = ruleFactory;
	}

	@Override
	public String format(List<AstComponent> asts) {
		List<String> formattedCodes = new ArrayList<>();
		FormatterVisitor visitor = new FormatterVisitor(ruleFactory);
		for (AstComponent ast : asts) {
			String formattedCode = ast.accept(visitor) + ";";
			formattedCodes.add(formattedCode);
		}
		return String.join("\n", formattedCodes);
	}
}
