package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintScriptSCA implements StaticCodeAnalyzer {
	private final Map<ConfigAttribute, String> configMap;

	      public PrintScriptSCA(ConfigReader configReader) throws IOException {
		this.configMap = configReader.read();
	}
	
	@Override
	public List<Result> analyze(String input) {
		List<Result> results = new ArrayList<>();
		for (ConfigAttribute entry : configMap.keySet()) {
			switch(entry) {
				case IDENTIFIER_FORMAT -> results.addAll(new IdentifierStrategy(configMap.get(entry)).analyze(input));
				case PRINTLN_EXPRESSIONS -> results.addAll(new PrintlnExpressionsStrategy(configMap.get(entry)).analyze(input));
				default -> throw new IllegalStateException("Unexpected value: " + entry);
			}
		}
		if (results.isEmpty()) {
			return List.of(new SuccessResult());
		}
		return results;
	}
}
