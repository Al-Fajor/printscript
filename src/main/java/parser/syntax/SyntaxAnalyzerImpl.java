package parser.syntax;

import model.*;
import parser.syntax.sentence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static model.BaseTokenTypes.*;

public class SyntaxAnalyzerImpl implements SyntaxAnalyzer{
  @Override
  public List<AstComponent> analyze(List<Token> tokens) {
    //TODO
    if(tokens.isEmpty()) return new ArrayList<>();
    return buildSentences(tokens);
  }

  private List<AstComponent> buildSentences(List<Token> tokens) {
    List<AstComponent> components;
    List<List<Token>> tokenSentences = getSentencesWithTokens(tokens);
    components = tokenSentences.stream().map(sentence -> initialTokenMap().get(sentence.get(0).getType()).buildSentence(sentence)).collect(Collectors.toList());
    return components;
  }

  private List<List<Token>> getSentencesWithTokens(List<Token> tokens) {
    int i = 0, j = 0;
    List<List<Token>> sentences = new ArrayList<>();
    while(i < tokens.size()){
      while(tokens.get(j).getType() != SEMICOLON) ++j;
      sentences.add(tokens.subList(i,j));
      i = j;
    }
    return sentences;
  }


  private Map<? extends TokenType, ? extends SentenceStrategy> initialTokenMap(){
    return Map.of(
      LET, new LetStrategy(),
      IF, new IfStrategy(),
      ELSE, new ElseStrategy(),
      PRINTLN, new PrintLineStrategy()
    );
  }
//  private Map<TokenType, Class<? extends AstComponent>> buildTokenMap() {
//    Map<TokenType, Class<? extends AstComponent>> map = new HashMap<>();
//    map.put(LET, Declaration.class);
//    map.put(TYPE, Declaration.class);
//    map.put(ASSIGNATION, Assignation.class);
//    map.put(IDENTIFIER, Identifier.class);
//    map.put(SEMICOLON, Identifier.class);
//    map.put(COLON, Identifier.class);
//    map.put(IF, IfStatement.class);
//    map.put(ELSE, IfStatement.class);
//    map.put(LITERAL, Literal.class);
//    map.put(OPERATOR, Parameters.class);
//    return map;
//  }


}
