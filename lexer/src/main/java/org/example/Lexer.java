package org.example;

import org.example.token.Token;

import java.util.List;

public interface Lexer {
    List<Token> lex(String input);
}
