package lexer;

import model.BaseTokenTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    Lexer lexer = new RegexLexer();

    @Test
    void integerInlineAssignation() {
        String input = "let x: number = 5;";
        var tokens = lexer.lex(input);
        System.out.println(tokens);
        assertEquals(7, tokens.size());
        assertEquals(BaseTokenTypes.LET, tokens.get(0).getType());
        assertEquals(BaseTokenTypes.IDENTIFIER, tokens.get(1).getType());
        assertEquals(BaseTokenTypes.COLON, tokens.get(2).getType());
        assertEquals(BaseTokenTypes.TYPE, tokens.get(3).getType());
        assertEquals(BaseTokenTypes.ASSIGNATION, tokens.get(4).getType());
        assertEquals(BaseTokenTypes.LITERAL, tokens.get(5).getType());
        assertEquals(BaseTokenTypes.SEMICOLON, tokens.get(6).getType());
    }

    @Test
    void stringInlineAssignation() {
        String input = "let x: string = \"hello\";";
        var tokens = lexer.lex(input);
        System.out.println(tokens);
        assertEquals(7, tokens.size());
        assertEquals(BaseTokenTypes.LET, tokens.get(0).getType());
        assertEquals(BaseTokenTypes.IDENTIFIER, tokens.get(1).getType());
        assertEquals(BaseTokenTypes.COLON, tokens.get(2).getType());
        assertEquals(BaseTokenTypes.TYPE, tokens.get(3).getType());
        assertEquals(BaseTokenTypes.ASSIGNATION, tokens.get(4).getType());
        assertEquals(BaseTokenTypes.LITERAL, tokens.get(5).getType());
        assertEquals(BaseTokenTypes.SEMICOLON, tokens.get(6).getType());
    }

    @Test
    void inlineVariableCreation() {
        String input = "let x: number;";
        var tokens = lexer.lex(input);
        System.out.println(tokens);
        assertEquals(5, tokens.size());
        assertEquals(BaseTokenTypes.LET, tokens.get(0).getType());
        assertEquals(BaseTokenTypes.IDENTIFIER, tokens.get(1).getType());
        assertEquals(BaseTokenTypes.COLON, tokens.get(2).getType());
        assertEquals(BaseTokenTypes.TYPE, tokens.get(3).getType());
        assertEquals(BaseTokenTypes.SEMICOLON, tokens.get(4).getType());
    }

    @Test
    void assignationWithSum() {
        String input = "let x: number = 5 + 3;";
        var tokens = lexer.lex(input);
        System.out.println(tokens);
        assertEquals(9, tokens.size());
        assertEquals(BaseTokenTypes.LET, tokens.get(0).getType());
        assertEquals(BaseTokenTypes.IDENTIFIER, tokens.get(1).getType());
        assertEquals(BaseTokenTypes.COLON, tokens.get(2).getType());
        assertEquals(BaseTokenTypes.TYPE, tokens.get(3).getType());
        assertEquals(BaseTokenTypes.ASSIGNATION, tokens.get(4).getType());
        assertEquals(BaseTokenTypes.LITERAL, tokens.get(5).getType());
        assertEquals(BaseTokenTypes.OPERATOR, tokens.get(6).getType());
        assertEquals(BaseTokenTypes.LITERAL, tokens.get(7).getType());
        assertEquals(BaseTokenTypes.SEMICOLON, tokens.get(8).getType());
    }

    @Test
    void invalidIdentifiers() {
        String input = "let 1x: number = 5;";
        assertThrows(IllegalArgumentException.class, () -> lexer.lex(input));
    }

    @Test
    void unterminatedStrings() {
        String input = "let x: string = \"hello;";
        assertThrows(IllegalArgumentException.class, () -> lexer.lex(input));
    }

    @Test
    void invalidNumberFormats() {
        String input = "let x: number = 5a5;";
        assertThrows(IllegalArgumentException.class, () -> lexer.lex(input));
    }
}