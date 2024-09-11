package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.example.ast.AstComponent;
import org.example.ast.statement.Statement;
import org.example.io.AstBuilder;
import org.example.io.RulesFromFile;
import org.json.JSONObject;

public class FormatterTestFramework {

	public void testRules(String path) throws IOException {
		AstBuilder astBuilder = new AstBuilder();
		RulesFromFile rulesFromFile = new RulesFromFile(path + "/rules.json");
		Formatter formatter = new PrintScriptFormatter(rulesFromFile.getRuleMap());
		Map<String, String> codes = getMapFromFile(path + "/codes.json");
		List<Path> cases = getAllFiles("src/test/resources/asts");
		for (Path testCase : cases) {
			String jsonPath = testCase.toString();
			String code = formatter.format(astBuilder.buildFromJson(jsonPath).iterator());
			assertEquals(codes.get(extractFileNameWithoutExtension(testCase)), code);
		}
	}

	private List<AstComponent> castToAstComponents(List<Statement> statements) {
		return statements.stream().map(statement -> (AstComponent) statement).toList();
	}

	private List<Path> getAllFiles(String directoryPath) throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
			return paths.filter(Files::isRegularFile).collect(Collectors.toList());
		}
	}

	private String extractFileNameWithoutExtension(Path path) {
		String fileName = path.getFileName().toString();
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex != -1) {
			return fileName.substring(0, dotIndex);
		}
		return fileName;
	}

	private Map<String, String> getMapFromFile(String path) {
		try {
			String content = Files.readString(Path.of(path));
			JSONObject jsonObject = new JSONObject(content);
			Map<String, String> map = new HashMap<>();
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				map.put(key, jsonObject.get(key).toString());
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException("Error while reading", e);
		}
	}
}
