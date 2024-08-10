package org.example;

import java.util.List;

public interface Lexer {
    List<Token> lex(String input);
}
