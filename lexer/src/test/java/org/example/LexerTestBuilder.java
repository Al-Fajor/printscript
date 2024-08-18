package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import org.example.io.FileParser;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerResult;
import org.example.token.Token;

public class LexerTestBuilder {

	public void testTokenDetection(String filePath) throws IOException {
		Lexer lexer = new PrintScriptLexer();
		FileParser fp = new FileParser();
		List<Token> expectedList = fp.getTokens(filePath);
		List<Token> actualList = lexer.lex(fp.getCode(filePath)).getTokens();
		compareTokens(expectedList, actualList);
	}

	private void compareTokens(List<Token> expectedList, List<Token> actualList) {
		assertEquals(expectedList.size(), actualList.size());
		for (int i = 0; i < expectedList.size(); i++) {
			assertEquals(expectedList.get(i).getType(), actualList.get(i).getType());
			assertEquals(expectedList.get(i).getValue(), actualList.get(i).getValue());
		}
	}

	public void testLexicalErrorDetection(String filePath) throws IOException {
		Lexer lexer = new PrintScriptLexer();
		FileParser fp = new FileParser();
		LexerResult result = lexer.lex(fp.getCode(filePath));
		assertInstanceOf(LexerFailure.class, result);
	}
}
