package org.example;

import org.example.factories.RuleFactory;

public class TestRuleMapFactory implements RuleFactory {
    private final String path;

    public TestRuleMapFactory(String path) {
        this.path = path;
    }

    @Override
    public Rules getRules() {
        RulesFromFile rulesFromFile = new RulesFromFile();
        return new Rules(rulesFromFile.getMapFromFile(path));
    }
}
