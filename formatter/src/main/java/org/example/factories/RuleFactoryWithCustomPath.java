package org.example.factories;

import org.example.FormatterRules;
import org.example.RulesFromFile;

public class RuleFactoryWithCustomPath implements RuleFactory {
	private final String path;

	public RuleFactoryWithCustomPath(String path) {
		this.path = path;
	}

	@Override
	public FormatterRules getRules() {
		RulesFromFile rulesFromFile = new RulesFromFile();
		return new FormatterRules(rulesFromFile.getMapFromFile(path));
	}
}
