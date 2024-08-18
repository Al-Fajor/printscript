package org.example;

import org.example.factories.RuleMapFactory;

import java.util.Map;

public class TestRuleMapFactory implements RuleMapFactory {
    private final String path;

    public TestRuleMapFactory(String path) {
        this.path = path;
    }

    @Override
    public Map<String, String> getRuleMap() {
        MapFromFile mapFromFile = new MapFromFile();
        return mapFromFile.getMapFromFile(path);
    }
}
