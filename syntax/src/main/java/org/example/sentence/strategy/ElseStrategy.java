package org.example.sentence.strategy;

import org.example.AstComponent;
import org.example.Token;

import java.util.List;

public class ElseStrategy implements SentenceStrategy {
  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    return null;
  }
}
