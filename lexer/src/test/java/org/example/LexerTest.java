package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    Lexer lexer = new PrintScriptLexer();
    LexerTestBuilder lexerTestBuilder = new LexerTestBuilder();

    @Test
    void integerInlineAssignation() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/integer_inline_assignation.json");
    }

    @Test
    void decimalInlineAssignation() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/decimal_inline_assignation.json");
    }

    @Test
    void stringInlineAssignation() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/string_inline_assignation.json");
    }

    @Test
    void variableCreation() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/variable_creation.json");
    }

    @Test
    void integerSumInlineAssignation() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/integer_sum_inline_assignation.json");
    }

    @Test
    void integerAssignation() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/integer_assignation.json");
    }

    @Test
    void stringMultilineInlineAssignations() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/string_multiline_inline_assignations.json");
    }

    @Test
    void printlnString() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/println_string.json");
    }

    @Test
    void printlnVariableTest() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/println_variable.json");
    }

    @Test
    void printCreatedVariable() throws IOException {
        lexerTestBuilder.testTokenDetection("src/test/resources/test_cases/valid/println_created_variable.json");
    }
    @Test
    void invalidIdentifiers() throws IOException {
        lexerTestBuilder.testLexicalErrorDetection("src/test/resources/test_cases/invalid/identifiers.json");
    }

    @Test
    void unterminatedStrings() throws IOException {
        lexerTestBuilder.testLexicalErrorDetection("src/test/resources/test_cases/invalid/unterminated_strings.json");
    }

    @Test
    void invalidNumberFormats() throws IOException {
        lexerTestBuilder.testLexicalErrorDetection("src/test/resources/test_cases/invalid/number_formats.json");
    }
}