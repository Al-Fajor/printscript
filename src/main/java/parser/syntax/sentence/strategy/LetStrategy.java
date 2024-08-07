package parser.syntax.sentence.strategy;

import model.*;
import parser.syntax.sentence.mapper.TokenMapper;
import parser.syntax.sentence.validator.LetSentenceValidator;
import parser.syntax.sentence.validator.SentenceValidator;

import java.util.List;

import static model.BaseTokenTypes.*;

public class LetStrategy implements SentenceStrategy{

  //LET -> IDENTIFIER("anything") -> COLON-> TYPE("anyType") -> ASSIGNATION -> ANYTHING -> SEMICOLON
  //AST (INORDER) SHOULD BE:
  // Number, Identifier(a), Declaration, Assignation, ANYTHING
  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    final SentenceValidator validator = new LetSentenceValidator();
    if(tokens.getFirst().getType() != LET || !validator.isValidSentence(tokens)) return null;
    return getFinalSentence(tokens);
  }

  private AstComponent getFinalSentence(List<Token> tokens) {
    TokenMapper mapper = new TokenMapper();
    //May need to change method
    Token type = tokens.get(3), identifier = tokens.get(1);
//    System.out.println("Type: " + type.getValue());
    DeclarationType declarationType = mapper.getDeclarationType(mapper.clearInvCommas(type.getValue()));

//    System.out.println("Identifier: " + identifier.getValue());
    AstComponent declaration = new Declaration(declarationType, mapper.clearInvCommas(identifier.getValue()));
    return new Assignation(declaration, mapper.buildFunctionArgument(tokens.subList(5, tokens.size())).getFirst());
  }

//  private int getIndexByTokenType(TokenType type, List<Token> tokens) {
//    for(Token token : tokens) {
//      if(token.getType() == type) {
//        return tokens.indexOf(token);
//      }
//    }
//    return -1;
//  }

}
