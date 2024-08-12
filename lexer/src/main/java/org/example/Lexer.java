package org.example;

import org.example.lexerresult.LexerResult;

public interface Lexer {
    LexerResult lex(String input);
}
