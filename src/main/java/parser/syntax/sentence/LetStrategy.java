package parser.syntax.sentence;

import model.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static model.BaseTokenTypes.*;

public class LetStrategy implements SentenceStrategy{
  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    //LET -> IDENTIFIER("anything") -> COLON-> TYPE -> ASSIGNATION -> ANYTHING -> SEMICOLON
    //AST (INORDER) SHOULD BE:
    // Number, Identifier(a), Declaration, Assignation, ANYTHING

    if(tokens.get(0).getType() != LET || !isValidSentence(tokens)) return null;
    Token identifier = tokens.get(getIndexByTokenType(IDENTIFIER, tokens));
    Token type = tokens.get(getIndexByTokenType(TYPE, tokens));


    System.out.println("Type value: " + type.getValue());
    DeclarationType declarationType = getDeclarationType(type.getValue().substring(1, type.getValue().length() - 1));
    System.out.println(declarationType);
    AstComponent declaration = new Declaration(declarationType, identifier.getValue().substring(1, identifier.getValue().length() - 1));
    Token literalToken = tokens.get(getIndexByTokenType(LITERAL, tokens));

    Literal<?> literal = parseValue(literalToken.getValue());
    System.out.println(literal.getValue());

    return new Assignation(declaration, literal);
  }

  private Literal<?> parseValue(String value) {
    System.out.println(value);
    if(value.contains("\"")){
      return  (Literal<String>) new Literal<>(value.substring(1, value.length()-1));
    }
    return (Literal<Integer>) new Literal<>(Integer.valueOf(value));
  }


  //Private
  private DeclarationType getDeclarationType(String type) {
    Map<String, DeclarationType> declarationTypeMap = Map.of(
      "number", DeclarationType.NUMBER,
      "string", DeclarationType.STRING,
      "function", DeclarationType.FUNCTION
    );
    return declarationTypeMap.get(type.toLowerCase());
  }

  private int getIndexByTokenType(TokenType type, List<Token> tokens) {
    for(Token token : tokens) {
      if(token.getType() == type) {
        return tokens.indexOf(token);
      }
    }
    return -1;
  }

  private boolean isValidSentence(List<Token> tokens) {
    Iterator<Token> iterator = tokens.iterator();
    while(iterator.hasNext()){
      Token token = iterator.next();
      switch ((BaseTokenTypes) token.getType()){
          case LET:
            if(iterator.next().getType() != IDENTIFIER) return false;
            break;
        case IDENTIFIER:
          if (iterator.next().getType() != COLON) return false;
          break;
        case SEMICOLON:
          if (iterator.hasNext()) return false;
          break;
        case COLON:
          if (iterator.next().getType() != TYPE) return false;
          break;
        case TYPE:
          if (iterator.next().getType() != ASSIGNATION) return false;
          break;
        case LITERAL:
          if (iterator.next().getType() != SEMICOLON) return false;
          break;
        default:
          return true;
      }
    }
    return true;
  }

}
