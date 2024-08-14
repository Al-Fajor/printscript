package org.example;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AstValidityChecker {
    public boolean readValidityFromJson(String filePath) throws IOException {
        File file = new File(filePath);
        String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
        JSONObject json = new JSONObject(content);
        return json.getBoolean("valid_semantics");
    }
}
