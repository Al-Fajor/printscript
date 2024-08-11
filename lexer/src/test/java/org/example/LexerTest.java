package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    Lexer lexer = new PrintScriptLexer();
    LexerTestBuilder lexerTestBuilder = new LexerTestBuilder();

    @Test
    void integerInlineAssignation() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/integer_inline_assignation.json");
    }

    @Test
    void decimalInlineAssignation() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/decimal_inline_assignation.json");
    }

    @Test
    void stringInlineAssignation() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/string_inline_assignation.json");
    }

    @Test
    void variableCreation() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/variable_creation.json");
    }

    @Test
    void integerSumInlineAssignation() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/integer_sum_inline_assignation.json");
    }

    @Test
    void integerAssignation() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/integer_assignation.json");
    }

    @Test
    void stringMultilineInlineAssignations() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/string_multiline_inline_assignations.json");
    }

    @Test
    void printlnString() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/println_string.json");
    }

    @Test
    void printlnVariableTest() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/println_variable.json");
    }

    @Test
    void printCreatedVariable() throws IOException {
        lexerTestBuilder.testLexer("src/test/resources/test_cases/print_created_variable.json");
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