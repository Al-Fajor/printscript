package org.example;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
	private final File configFile;

	public ConfigReader(String path) {
		configFile = new File(path);
	}

	public Map<ConfigAttribute, String> read() throws IOException {
		JSONObject config = getConfigObject();
		Map<ConfigAttribute, String> configMap = new HashMap<>();
		for (String key : config.keySet()) {
			ConfigAttribute attribute = ConfigAttribute.fromJsonKey(key);
			String value = config.get(key).toString();
			if (attribute.valueIsAllowed(value)) {
				configMap.put(attribute, value);
			} else {
				throw new IllegalArgumentException("Value " + value + " is not allowed for attribute " + key);
			}
		}
		return configMap;
	}

	private JSONObject getConfigObject() throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(configFile.toURI())));
		return new JSONObject(content);
	}
}
