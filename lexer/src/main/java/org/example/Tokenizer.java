package org.example;

import java.util.Iterator;

import org.example.iterators.TokenIterator;
import org.example.token.Token;

public class Tokenizer {
    public Iterator<Token> tokenize(String input) {
        return new TokenIterator(input);
    }
}