package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.example.io.FileParser;
import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;
import org.example.utils.StringToIterator;

public class LexerTestBuilder {

	public void testTokenDetection(String filePath) throws IOException {
		Lexer lexer = new PrintScriptLexer();
		FileParser fp = new FileParser();
		Iterator<Token> expectedList = fp.getTokens(filePath).iterator();
		Result result = lexer.lex(StringToIterator.convert(fp.getCode(filePath)));
		if (result.isSuccessful()) {
			LexerSuccess success = (LexerSuccess) result;
			Iterator<Token> actualList = success.getTokens();
			compareTokens(expectedList, actualList);
		} else {
			fail("Lexer failed to parse the file");
		}
	}

	private void compareTokens(Iterator<Token> expectedList, Iterator<Token> actualList) {
        while (expectedList.hasNext() && actualList.hasNext()) {
            Token expected = expectedList.next();
            Token actual = actualList.next();
            assertEquals(expected.getType(), actual.getType());
            assertEquals(expected.getValue(), actual.getValue());
        }
        assertFalse(expectedList.hasNext());
        assertFalse(actualList.hasNext());
	}

	public void testLexicalErrorDetection(String filePath) throws IOException {
		Lexer lexer = new PrintScriptLexer();
		FileParser fp = new FileParser();
		Result result = lexer.lex(StringToIterator.convert(fp.getCode(filePath)));
		assertFalse(result.isSuccessful());
		System.out.println(result.errorMessage());
	}
}
