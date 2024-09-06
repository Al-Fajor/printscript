package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PrintScriptSca implements StaticCodeAnalyzer {
	private final Map<ConfigAttribute, String> configMap;

	public PrintScriptSca(ConfigReader configReader) throws IOException {
		this.configMap = configReader.read();
	}

	@Override
	public List<Result> analyze(Iterator<String> input) {
		List<Result> results = new ArrayList<>();
		while (input.hasNext()) {
			String line = input.next();
			for (ConfigAttribute entry : configMap.keySet()) {
				switch (entry) {
					case IDENTIFIER_FORMAT ->
							results.addAll(
									new IdentifierStrategy(configMap.get(entry)).analyze(line));
					case PRINTLN_EXPRESSIONS ->
							results.addAll(
									new PrintlnExpressionsStrategy(configMap.get(entry))
											.analyze(line));
					default -> throw new IllegalStateException("Unexpected value: " + entry);
				}
			}
		}

		if (results.isEmpty()) {
			return List.of(new SuccessResult());
		}
		return results;
	}
}
