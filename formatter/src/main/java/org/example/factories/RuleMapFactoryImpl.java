package org.example.factories;

import org.example.MapFromFile;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RuleMapFactoryImpl implements RuleMapFactory {
    @Override
    public Map<String, String> getRuleMap() {
        MapFromFile mapFromFile = new MapFromFile();
        String PATH = "src/main/resources/formatter.rules.json";
        return mapFromFile.getMapFromFile(PATH);
    }
}
