package org.example.lexerresult;

import org.example.token.Token;

import java.util.List;

public interface LexerResult {
    List<Token> getTokens();
    boolean isSuccessful();
}
