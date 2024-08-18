package org.example.factories;

import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RuleMapFactory {
    public Map<String, String> getRuleMap() {
        String PATH = "src/main/resources/formatter.rules.json";
        try {
            String content = Files.readString(Path.of(PATH));
            JSONObject jsonObject = new JSONObject(content);
            Map<String, String> map = new HashMap<>();
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                map.put(key, jsonObject.get(key).toString());
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException("Error while reading formatter rules", e);
        }
    }
}
