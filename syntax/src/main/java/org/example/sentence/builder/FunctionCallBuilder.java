package org.example.sentence.builder;

import org.example.ast.*;
import org.example.ast.statement.FunctionCallStatement;
import org.example.token.Token;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.FunctionSentenceValidator;
import org.example.sentence.validator.SentenceValidator;

import java.util.List;

import static org.example.token.BaseTokenTypes.PRINTLN;

public class FunctionCallBuilder implements SentenceBuilder {
  // FUNCTION -> SEPARATOR("(") -> ANYTHING -> SEPARATOR(")") -> ANYTHING -> SEMICOLON

  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    SentenceValidator validator = new FunctionSentenceValidator();
    if(tokens.getFirst().getType() != PRINTLN || !validator.isValidSentence(tokens)) return null;
    return getFinalSentence(tokens);
  }

  private AstComponent getFinalSentence(List<Token> tokens) {
    List<EvaluableComponent> parameters = new TokenMapper().buildArgument(tokens.subList(1, tokens.size()));
    System.out.println(parameters);

    return new FunctionCallStatement(new Identifier("println", IdentifierType.FUNCTION),
      new Parameters(parameters));
  }
}
