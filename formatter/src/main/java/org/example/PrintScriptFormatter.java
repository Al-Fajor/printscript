package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.example.ast.statement.Statement;

public class PrintScriptFormatter implements Formatter {
	private final Map<String, String> ruleMap;

	public PrintScriptFormatter(Map<String, String> ruleMap) {
		this.ruleMap = ruleMap;
	}

	@Override
	public String format(Iterator<Statement> asts) {
		List<String> formattedCodes = new ArrayList<>();
		FormatterVisitor visitor = new FormatterVisitor(new RuleProvider(ruleMap));
		while (asts.hasNext()) {
			Statement ast = asts.next();
			String formattedCode = ast.accept(visitor) + addSemicolon(ast.accept(visitor));
            formattedCodes.add(formattedCode);
		}
		return String.join("\n", formattedCodes);
	}

    private String addSemicolon(String formattedCode) {
        if (formattedCode.charAt(formattedCode.length() - 1) != '}') {
            return ";";
        }
        return "";
    }
}
