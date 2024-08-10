package org.example.sentence.strategy;

import org.example.ast.statement.AssignationStatement;
import org.example.ast.AstComponent;
import org.example.ast.statement.DeclarationStatement;
import org.example.ast.DeclarationType;
import org.example.token.Token;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.LetSentenceValidator;
import org.example.sentence.validator.SentenceValidator;

import java.util.List;

import static org.example.token.BaseTokenTypes.LET;

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
    AstComponent declaration = new DeclarationStatement(declarationType, mapper.clearInvCommas(identifier.getValue()));
    return new AssignationStatement(declaration, mapper.buildFunctionArgument(tokens.subList(5, tokens.size())).getFirst());
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
