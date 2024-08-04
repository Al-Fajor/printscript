package parser.syntax.sentence;

import model.AstComponent;
import model.Token;

import java.util.List;

public interface SentenceStrategy {
  AstComponent buildSentence(List<Token> tokens);
}
