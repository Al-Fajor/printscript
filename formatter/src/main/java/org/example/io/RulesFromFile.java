package org.example.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.json.JSONObject;

public class RulesFromFile {
    private final String path;

    public RulesFromFile (String path) {
        this.path = path;
    }

	public Map<String, String> getRuleMap() {
        try {
            String content = Files.readString(Path.of(path));
            JSONObject jsonObject = new JSONObject(content);
            Map<String, String> map = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                map.put(key, jsonObject.get(key).toString());
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
}
