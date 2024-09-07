package org.example;

import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;

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
        Lexer lexer = new PrintScriptLexer();
        Result lexerResult = lexer.lex(input);
        if (!lexerResult.isSuccessful()){
            throw new RuntimeException("Lexer failed: " + lexerResult);
        }
        Iterator<Token> tokenIterator = ((LexerSuccess) lexerResult).getTokens();
        for (ConfigAttribute entry : configMap.keySet()) {
            switch (entry) {
                case IDENTIFIER_FORMAT ->
                        results.addAll(
                                new IdentifierStrategy(configMap.get(entry)).analyze(tokenIterator));
                case PRINTLN_EXPRESSIONS ->
                        results.addAll(
                                new PrintlnExpressionsStrategy(configMap.get(entry))
                                        .analyze(tokenIterator));
                default -> throw new IllegalStateException("Unexpected value: " + entry);
            }
        }

		if (results.isEmpty()) {
			return List.of(new SuccessResult());
		}
		return results;
	}
}
