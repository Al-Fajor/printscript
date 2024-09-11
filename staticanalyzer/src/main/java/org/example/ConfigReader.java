package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ConfigReader {

	public Map<ConfigAttribute, String> read(InputStream config) throws IOException {
		JSONObject configObj = getConfigObject(config);
		Map<ConfigAttribute, String> configMap = new HashMap<>();
		for (String key : configObj.keySet()) {
			ConfigAttribute attribute = ConfigAttribute.fromJsonKey(key);
			String value = configObj.get(key).toString();
			if (attribute.valueIsAllowed(value)) {
				configMap.put(attribute, value);
			} else {
				throw new IllegalArgumentException(
						"Value " + value + " is not allowed for attribute " + key);
			}
		}
		return configMap;
	}

	private JSONObject getConfigObject(InputStream config) throws IOException {
		String content = new String(config.readAllBytes());
		return new JSONObject(content);
	}
}
