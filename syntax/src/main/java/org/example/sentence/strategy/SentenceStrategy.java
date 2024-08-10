package org.example.sentence.strategy;

import org.example.ast.AstComponent;
import org.example.token.Token;

import java.util.List;

public interface SentenceStrategy {
  AstComponent buildSentence(List<Token> tokens);
}
