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
    if(tokens.get(0).getType() != LET || !validator.isValidSentence(tokens)) return null;
    return getFinalSentence(tokens);
  }

  private AstComponent getFinalSentence(List<Token> tokens) {
    TokenMapper mapper = new TokenMapper();
    //May need to change method
    Token type = tokens.get(3), identifier = tokens.get(1);

    DeclarationType declarationType = mapper.getDeclarationType(type.getValue().substring(1, type.getValue().length() - 1));

    AstComponent declaration = new Declaration(declarationType, identifier.getValue().substring(1, identifier.getValue().length() - 1));
    return new Assignation(declaration, mapper.buildArgument(tokens.subList(5, tokens.size())).get(0));
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
