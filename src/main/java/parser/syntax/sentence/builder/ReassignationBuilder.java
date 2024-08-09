package parser.syntax.sentence.builder;

import model.*;
import parser.syntax.sentence.mapper.TokenMapper;

import java.util.List;
import java.util.stream.IntStream;

import static model.BaseTokenTypes.ASSIGNATION;
import static model.BaseTokenTypes.IDENTIFIER;

public class ReassignationBuilder implements SentenceBuilder{
  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    if(tokens.getFirst().getType() != IDENTIFIER || tokens.get(1).getType() != ASSIGNATION) return null;

    Token identifier = tokens.get(0);
    List<Token> tokensFromAssignation = tokens.subList(getFirstAssignationIndex(tokens) + 1,tokens.size());

    TokenMapper mapper = new TokenMapper();
    AstComponent rightHandSide = mapper.buildArgument(tokensFromAssignation).getFirst();

    return new Assignation(new Identifier(mapper.clearInvCommas(identifier.getValue()), IdentifierType.VARIABLE), rightHandSide );
  }

  private int getFirstAssignationIndex(List<Token> tokens) {
    return IntStream.range(0, tokens.size()).filter(i -> tokens.get(i).getType() == ASSIGNATION).findFirst().orElse(-1);
  }
}
