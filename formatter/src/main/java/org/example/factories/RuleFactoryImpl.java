package org.example.factories;

import org.example.RulesFromFile;
import org.example.FormatterRules;
import org.example.ast.AstComponent;
import org.example.ruleappliers.RuleApplier;

import java.util.List;

public class RuleFactoryImpl implements RuleFactory {
    @Override
    public FormatterRules getRules() {
        RulesFromFile rulesFromFile = new RulesFromFile();
        String PATH = "src/main/resources/rules.json";
        List<RuleApplier<? extends AstComponent>> ruleAppliers = rulesFromFile.getMapFromFile(PATH);
        return new FormatterRules(ruleAppliers);
    }
}
