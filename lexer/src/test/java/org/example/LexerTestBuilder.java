package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.example.io.FileParser;
import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;

public class LexerTestBuilder {

	public void testTokenDetection(String filePath, String version) throws IOException {
		Lexer lexer = new PrintScriptLexer(version);
		FileParser fp = new FileParser();
		Iterator<Token> expectedList = fp.getTokens(filePath).iterator();
		Iterator<String> input =
				List.of(
								fp.getCode(filePath)
										.split(
												"(?<=\\}|(?<!\\{[^{}]);(?![^{}]*\\}))(?=(?!.*else).*)"))
						.iterator();
		while (input.hasNext()) {
			Result result = lexer.lex(input);
			if (result.isSuccessful()) {
				LexerSuccess success = (LexerSuccess) result;
				Iterator<Token> actualList = success.getTokens();
				compareTokens(expectedList, actualList);
			} else {
				fail("Lexer failed to parse the file");
			}
			//			System.out.println(input.hasNext());
		}
		assertFalse(expectedList.hasNext());
	}

	private void compareTokens(Iterator<Token> expectedList, Iterator<Token> actualList) {
		while (expectedList.hasNext() && actualList.hasNext()) {
			Token expected = expectedList.next();
			Token actual = actualList.next();
			assertEquals(expected.getType(), actual.getType());
			assertEquals(expected.getValue(), actual.getValue());
		}
		assertFalse(actualList.hasNext());
	}

	public void testLexicalErrorDetection(String filePath, String version) throws IOException {
		Lexer lexer = new PrintScriptLexer(version);
		FileParser fp = new FileParser();
		Result result =
				lexer.lex(
						List.of(
										fp.getCode(filePath)
												.split(
														"(?<=\\}|(?<!\\{[^{}]);(?![^{}]*\\}))(?=(?!.*else).*)"))
								.iterator());
		assertFalse(result.isSuccessful());
		//		System.out.println(result.errorMessage());
	}
}
