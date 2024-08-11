package org.example;

import org.example.token.Token;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

public class LexerTestBuilder {

    public void testLexer(String filePath) throws IOException {
        Lexer lexer = new PrintScriptLexer();
        FileParser fp = new FileParser();
        List<Token> expectedList = fp.getTokens(filePath);
        List<Token> actualList = lexer.lex(fp.getCode(filePath));
        compareTokens(expectedList, actualList);
    }

    private void compareTokens(List<Token> expectedList, List<Token> actualList) {
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getType(), actualList.get(i).getType());
            assertEquals(expectedList.get(i).getValue(), actualList.get(i).getValue());
        }
    }

}
