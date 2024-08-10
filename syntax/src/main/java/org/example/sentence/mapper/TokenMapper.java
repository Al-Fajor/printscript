package org.example.sentence.mapper;

import org.example.ast.AstComponent;
import org.example.ast.BinaryExpression;
import org.example.ast.BinaryOperator;
import org.example.ast.DeclarationType;
import org.example.ast.IdentifierType;
import org.example.ast.Literal;
import org.example.token.Token;
import org.example.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.example.token.BaseTokenTypes.FUNCTION;
import static org.example.token.BaseTokenTypes.IDENTIFIER;
import static org.example.token.BaseTokenTypes.LITERAL;
import static org.example.token.BaseTokenTypes.OPERATOR;
import static org.example.token.BaseTokenTypes.SEMICOLON;
import static org.example.token.BaseTokenTypes.SEPARATOR;

public class TokenMapper {
  public List<AstComponent> buildFunctionArgument(List<Token> tokens) {
    List<AstComponent> arguments = new ArrayList<>();
    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);

      if (token.getType() == SEMICOLON) {
        break;
      }
      if(allTokensAreAtomic(tokens)){
        arguments.add(mapToken(token));
      }

      if(token.getType() == OPERATOR){
        if(i+1>=tokens.size() || i-1<0) continue;
        arguments.add(new BinaryExpression(mapOperator(token.getValue()),
          mapToken(tokens.get(i-1)),mapToken(tokens.get(i+1))));
      }

      if(token.getType() == SEPARATOR){
        if(token.getValue().equals("\"(\"") || token.getValue().equals("(")){
          return buildFunctionArgument(tokens.subList(i+1, findFirstClosingSeparator(tokens)));
        }
      }
      //TODO: REEVALUATE FUNCTION CASE, MAY NEED TO ADD BRACKETS AND BRACES
    }
    return arguments;
  }

  private int findFirstClosingSeparator(List<Token> tokens) {
    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);
      if (token.getType() == SEPARATOR && (token.getValue().equals(")") || token.getValue().equals("\")\""))) {
        return i;
      }
    }
    return tokens.size();
  }

  private boolean allTokensAreAtomic(List<Token> tokens) {
    return tokens.stream().allMatch(tk -> notCompoundComponent(tk.getType()));
  }

  public AstComponent mapToken(Token token) {
    Map<TokenType, AstComponent> map = Map.of(
      LITERAL, translateToLiteral(token.getValue()),
      IDENTIFIER, new Identifier(clearInvCommas(token.getValue()), IdentifierType.VARIABLE)
    );
    return map.get(token.getType());
  }

  private Literal<?> translateToLiteral(String value){
    if(value.contains("\"") || !isNumeric(value)){
        return new Literal<>(clearInvCommas(value));
      }
      return new Literal<>(Integer.valueOf(value));
  }

  private boolean isNumeric(String value) {
    Pattern pattern = Pattern.compile("[0-9]+");
    return pattern.matcher(value).matches();
  }

  private boolean notCompoundComponent(TokenType type) {
    List<TokenType> compoundTypes = List.of(FUNCTION, OPERATOR, SEPARATOR);
    return !compoundTypes.contains(type);
  }

  private BinaryOperator mapOperator(String value) {
    System.out.println("Operator: " + value);

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

  public String clearInvCommas(String value){
    if(value.charAt(0) == '\"' && value.charAt(value.length()-1) == '\"'){
      return value.substring(1, value.length()-1);
    }
    return value;
  }


}
