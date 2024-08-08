package parser.syntax.sentence.builder;

import model.AstComponent;
import model.Token;

import java.util.List;

public interface SentenceBuilder {
  AstComponent buildSentence(List<Token> tokens);
}
