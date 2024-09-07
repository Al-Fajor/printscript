package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.example.lexerresult.LexerSuccess;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;

public class PrintScriptSca implements StaticCodeAnalyzer {
	private final ConfigReader configReader = new ConfigReader();
	private final Map<ConfigAttribute, String> configMap;

	public PrintScriptSca(InputStream config) throws IOException {
		this.configMap = configReader.read(config);
	}

	@Override
	public List<Result> analyze(Iterator<String> input) {
		List<Result> results = new ArrayList<>();
		Lexer lexer = new PrintScriptLexer();
		Result lexerResult = lexer.lex(input);
		if (!lexerResult.isSuccessful()) {
			throw new RuntimeException("Lexer failed: " + lexerResult);
		}
		Iterator<Token> tokenIterator = ((LexerSuccess) lexerResult).getTokens();
		List<Token> tokenList = new ArrayList<>();
		while (tokenIterator.hasNext()) {
			Token token = tokenIterator.next();
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
				default -> throw new IllegalStateException("Unexpected value: " + entry);
			}
		}
	}
}
