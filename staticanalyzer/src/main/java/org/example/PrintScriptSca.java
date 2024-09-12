package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.example.result.SuccessResult;
import org.example.strategy.IdentifierStrategy;
import org.example.strategy.PrintlnExpressionsStrategy;
import org.example.strategy.ReadInputExpressionsStrategy;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;

public class PrintScriptSca implements StaticCodeAnalyzer {
	private final Map<ConfigAttribute, String> configMap;

	public PrintScriptSca(InputStream config) throws IOException {
		ConfigReader configReader = new ConfigReader();
		this.configMap = configReader.read(config);
	}

	@Override
	public List<Result> analyze(Iterator<Token> input) {
		List<Result> results = new ArrayList<>();
		List<Token> tokenList = new ArrayList<>();
		while (input.hasNext()) {
			Token token = input.next();
			tokenList.add(token);
			if (isEndOfBlock(token)) {
				analyzeTokens(tokenList, results);
				tokenList.clear();
			}
		}

		if (results.isEmpty()) {
			return List.of(new SuccessResult());
		}
		return results;
	}

	private boolean isEndOfBlock(Token token) {
		return token.getType().equals(BaseTokenTypes.SEMICOLON)
				|| (token.getType().equals(BaseTokenTypes.SEPARATOR)
						&& token.getValue().equals("}"));
	}

	private void analyzeTokens(List<Token> tokens, List<Result> results) {
		for (ConfigAttribute entry : configMap.keySet()) {
			switch (entry) {
				case IDENTIFIER_FORMAT ->
						results.addAll(
								new IdentifierStrategy(configMap.get(entry))
										.analyze(tokens.iterator()));
				case PRINTLN_EXPRESSIONS ->
						results.addAll(
								new PrintlnExpressionsStrategy(configMap.get(entry))
										.analyze(tokens.iterator()));
				case READ_INPUT_EXPRESSIONS ->
						results.addAll(
								new ReadInputExpressionsStrategy(configMap.get(entry))
										.analyze(tokens.iterator()));
				default -> throw new IllegalStateException("Unexpected value: " + entry);
			}
		}
	}
}
