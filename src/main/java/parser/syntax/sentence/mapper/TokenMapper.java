package parser.syntax.sentence.mapper;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.BaseTokenTypes.*;
import static model.BaseTokenTypes.OPERATOR;

public class TokenMapper {
  public List<AstComponent> buildArgument(List<Token> tokens) {
    List<AstComponent> arguments = new ArrayList<>();
    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);

      if (token.getType() == SEMICOLON) {
        break;
      }
      if(notCompoundComponent(token.getType()) && tokens.stream().allMatch(tk -> notCompoundComponent(tk.getType()))){
        arguments.add(mapToken(token));
      }
      if(token.getType() == OPERATOR){
        arguments.add(new BinaryExpression(mapOperator(token.getValue()),
          mapToken(tokens.get(i-1)),mapToken(tokens.get(i+1))));
      }
      //TODO: REEVALUATE FUNCTION CASE, MAY NEED TO ADD BRACKETS AND BRACES
    }
    return arguments;
  }

  public AstComponent mapToken(Token token) {
    Map<TokenType, AstComponent> map = Map.of(
      LITERAL, translateToLiteral(token.getValue()),
      IDENTIFIER, new Identifier(cleanInvCommas(token.getValue()), IdentifierType.VARIABLE)
    );
    return map.get(token.getType());
  }



  private Literal<?> translateToLiteral(String value){
    if(value.contains("\"")){
        return new Literal<>(cleanInvCommas(value));
      }
      return new Literal<>(Integer.valueOf(value));
  }

  private boolean notCompoundComponent(TokenType type) {
    List<TokenType> compoundTypes = List.of(FUNCTION, OPERATOR);
    return !compoundTypes.contains(type);
  }

  private BinaryOperator mapOperator(String value) {
    Map<String, BinaryOperator> map = Map.of(
      "+", BinaryOperator.SUM,
      "-", BinaryOperator.SUBTRACTION,
      "*", BinaryOperator.MULTIPLICATION,
      "/", BinaryOperator.DIVISION
    );
    return map.get(value);
  }

  public DeclarationType getDeclarationType(String type) {
    Map<String, DeclarationType> declarationTypeMap = Map.of(
      "number", DeclarationType.NUMBER,
      "string", DeclarationType.STRING,
      "function", DeclarationType.FUNCTION
    );
    return declarationTypeMap.get(type.toLowerCase());
  }

  private String cleanInvCommas(String value){
    if(value.charAt(0) == '\"' && value.charAt(value.length()-1) == '\"'){
      return value.substring(1, value.length()-1);
    }
    return value;
  }
}
