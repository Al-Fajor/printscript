package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PrintScriptSCA implements StaticCodeAnalyzer {
	private final Map<ConfigAttribute, String> configMap;

	public PrintScriptSCA() throws IOException {
		this.configMap = new ConfigReader("staticanalyzer/src/main/resources/sca-config.json").read();
	}
	
	@Override
	public List<Result> analyze(String input) {
		return null;
	}
}
