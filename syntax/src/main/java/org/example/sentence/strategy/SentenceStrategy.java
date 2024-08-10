package org.example.sentence.strategy;

import org.example.AstComponent;
import org.example.Token;

import java.util.List;

public interface SentenceStrategy {
  AstComponent buildSentence(List<Token> tokens);
}
