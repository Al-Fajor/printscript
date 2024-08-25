package org.example;

import org.example.ast.AstComponent;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;
import org.example.ruleappliers.assignation.SpaceAroundEquals;
import org.example.ruleappliers.declaration.SpaceAfterColon;
import org.example.ruleappliers.declaration.SpaceBeforeColon;
import org.example.ruleappliers.function.BreaksBeforePrintln;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class RulesFromFile {
    public List<RuleApplier<? extends AstComponent>> getMapFromFile(String path) {
        try {
            String content = Files.readString(Path.of(path));
            JSONObject jsonObject = new JSONObject(content);
            Iterator<String> keys = jsonObject.keys();
            List<RuleApplier<?>> ruleAppliers = new ArrayList<>();
            while (keys.hasNext()) {
                String key = keys.next();
                ruleAppliers.add(getRuleApplier(key, jsonObject));
            }
            return ruleAppliers;
        } catch (Exception e) {
            throw new RuntimeException("Error while reading formatter rules", e);
        }
    }

    private RuleApplier<?> getRuleApplier(String key, JSONObject jsonObject) {
        return switch (key) {
            case "spaceBeforeColon" -> new SpaceBeforeColon(jsonObject.getBoolean(key));
            case "spaceAfterColon" -> new SpaceAfterColon(jsonObject.getBoolean(key));
            case "spaceAroundEquals" -> new SpaceAroundEquals(jsonObject.getBoolean(key));
            case "breaksBeforePrintln" -> new BreaksBeforePrintln(jsonObject.getInt(key));
            default -> throw new RuntimeException("Unknown rule: " + key);
        };
    }
}
