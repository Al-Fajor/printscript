package org.example;

import org.example.factories.RuleFactory;

public class TestRuleMapFactory implements RuleFactory {
    private final String path;

    public TestRuleMapFactory(String path) {
        this.path = path;
    }

    @Override
    public FormatterRules getRules() {
        RulesFromFile rulesFromFile = new RulesFromFile();
        return new FormatterRules(rulesFromFile.getMapFromFile(path));
    }
}
