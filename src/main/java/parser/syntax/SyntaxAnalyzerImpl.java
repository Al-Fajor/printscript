package parser.syntax;

import model.*;
import parser.syntax.sentence.strategy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static model.BaseTokenTypes.*;

public class SyntaxAnalyzerImpl implements SyntaxAnalyzer{
  @Override
  public List<AstComponent> analyze(List<Token> tokens) {
    if(tokens.isEmpty()) return new ArrayList<>();
    return buildSentences(tokens);
  }

  private List<AstComponent> buildSentences(List<Token> tokens) {
    try{
      List<AstComponent> components;
      List<List<Token>> tokenSentences = getSentencesWithTokens(tokens);
//      replaceRepeatedIdentifiers(tokenSentences);
      components = tokenSentences.stream().map(sentence -> initialTokenMap().get(sentence.getFirst().getType()).buildSentence(sentence)).collect(Collectors.toList());

      return components.contains(null) ? List.of() : components;
    } catch (NullPointerException e){
      return List.of();
    }
  }

  private List<List<Token>> getSentencesWithTokens(List<Token> tokens) {
    List<List<Token>> sentences = new ArrayList<>();
    int i = 0;
    for (int j = 0; j < tokens.size() ; j++) {
      if(tokens.get(j).getType() ==SEMICOLON) {
        sentences.add(tokens.subList(i,j));
        i=j+1;
        if(i>=tokens.size()) break;
      }
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


}
