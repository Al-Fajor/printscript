package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import org.example.io.FileParser;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;

public class LexerTestBuilder {

	public void testTokenDetection(String filePath) throws IOException {
		Lexer lexer = new PrintScriptLexer();
		FileParser fp = new FileParser();
		List<Token> expectedList = fp.getTokens(filePath);
        Result result = lexer.lex(fp.getCode(filePath));
        if (result.isSuccessful()) {
            LexerSuccess success = (LexerSuccess) result;
            List<Token> actualList = success.getTokens();
            compareTokens(expectedList, actualList);
        } else {
            fail("Lexer failed to parse the file");
        }
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
		Result result = lexer.lex(fp.getCode(filePath));
		assertFalse(result.isSuccessful());
	}
}
