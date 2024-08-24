package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class CliTestReader {
	public static String readCommand(String filePath) throws IOException {
		File file = new File(filePath);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		JSONObject json = new JSONObject(content);
		return json.getString("command");
	}

	public static List<String> readOutput(String filePath) throws IOException {
		File file = new File(filePath);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		JSONObject json = new JSONObject(content);

		List<String> result = new ArrayList<>();
		json.getJSONArray("expectedOutput")
				.iterator()
				.forEachRemaining(string -> result.add((String) string));
		return result;
	}
}
