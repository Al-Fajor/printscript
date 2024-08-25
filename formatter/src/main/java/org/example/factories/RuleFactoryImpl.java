package org.example.factories;

import org.example.RulesFromFile;
import org.example.Rules;
import org.example.ast.AstComponent;
import org.example.ruleappliers.RuleApplier;

import java.util.List;

public class RuleFactoryImpl implements RuleFactory {
    @Override
    public Rules getRules() {
        RulesFromFile rulesFromFile = new RulesFromFile();
        String PATH = "src/main/resources/rules.json";
        List<RuleApplier<? extends AstComponent>> ruleAppliers = rulesFromFile.getMapFromFile(PATH);
        return new Rules(ruleAppliers);
    }
}
