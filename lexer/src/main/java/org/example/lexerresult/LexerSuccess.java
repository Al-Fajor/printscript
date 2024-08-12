package org.example.lexerresult;

import org.example.token.Token;

import java.util.List;

public class LexerSuccess implements LexerResult {
    private final List<Token> tokens;

    public LexerSuccess(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public List<Token> getTokens() {
        return tokens;
    }

    @Override
    public boolean isSuccessful() {
        return true;
    }
}
