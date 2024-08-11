package org.example.sentence.builder;

import org.example.ast.AstComponent;
import org.example.token.Token;

import java.util.List;

public interface SentenceBuilder {
  AstComponent buildSentence(List<Token> tokens);
}
