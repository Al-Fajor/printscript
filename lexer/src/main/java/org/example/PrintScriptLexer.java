package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.example.iterators.ConcatenatedIterator;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;

public class PrintScriptLexer implements Lexer {

	@Override
	public Result lex(Iterator<String> input) {
        System.out.println("aha");
		Scanner scanner = new Scanner();
		Tokenizer tokenizer = new Tokenizer();
		//        TODO change this if not memory efficient enough
		List<Iterator<Token>> tokenIterators = new ArrayList<>();

        int lineNumber = 0;
		while (input.hasNext()) {
            lineNumber++;
			String line = input.next();
            System.out.println(line);
            System.out.println(lineNumber);
			Result scanResult = scanner.scan(line, lineNumber);
			if (!scanResult.isSuccessful()) {
				return new LexerFailure(scanResult);
			}
			tokenIterators.add(tokenizer.tokenize(line, lineNumber));
		}

		Iterator<Token> concatenatedTokens = new ConcatenatedIterator(tokenIterators);
		return new LexerSuccess(concatenatedTokens);
	}
}
