package parser.syntax.sentence;

import model.*;

import java.util.List;

import static model.BaseTokenTypes.ASSIGNATION;
import static model.BaseTokenTypes.LET;

public class LetStrategy implements SentenceStrategy{
  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    //LET_KEYWORD -> IDENTIFIER(a) -> COLON-> NUMBER_TYPE -> ASSIGNATION -> LITERAL(5)
    //AST (INORDER) SHOULD BE:
    // Number, Identifier(a), Declaration, Assignation, Literal(5)

//    if(tokens.get(0).getType() != LET || !isValidSentence(tokens)) return null;
    int assignationIndex = getIndexByTokenType(ASSIGNATION, tokens);
    int i = assignationIndex;
    while (i>0){

    }

    return null;
  }

  private int getIndexByTokenType(TokenType type, List<Token> tokens) {
    for(Token token : tokens) {
      if(token.getType() == type) {
        return tokens.indexOf(token);
      }
    }
    return -1;
  }
}
