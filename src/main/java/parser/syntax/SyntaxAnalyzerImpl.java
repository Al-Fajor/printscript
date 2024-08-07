package parser.syntax;

import model.*;
import parser.syntax.result.SyntaxError;
import parser.syntax.result.SyntaxResult;
import parser.syntax.result.SyntaxSuccess;
import parser.syntax.sentence.strategy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static model.BaseTokenTypes.*;

public class SyntaxAnalyzerImpl implements SyntaxAnalyzer{
  @Override
  public SyntaxResult analyze(List<Token> tokens) {
    if(tokens.isEmpty()) return new SyntaxSuccess(List.of());
    return buildSentences(tokens);
  }

  private SyntaxResult buildSentences(List<Token> tokens) {
    try{
      List<AstComponent> components;
      List<List<Token>> tokenSentences = getSentencesWithTokens(tokens);
//      replaceRepeatedIdentifiers(tokenSentences);
      components = tokenSentences.stream().map(sentence -> initialTokenMap().get(sentence.getFirst().getType()).buildSentence(sentence)).collect(Collectors.toList());

      return components.contains(null) ?
        new SyntaxError("Invalid sentence at index: " + components.indexOf(null) + ";\n" +
          " Starting token: " + tokenSentences.get(components.indexOf(null)).getFirst()) :
        new SyntaxSuccess(components);

    } catch (NullPointerException e){
      return new SyntaxError("Invalid tokens");
    }
  }

  private List<List<Token>> getSentencesWithTokens(List<Token> tokens) {
    List<List<Token>> sentences = new ArrayList<>();
    int i = 0;
    for (int j = 0; j < tokens.size() ; j++) {
      if(tokens.get(j).getType() ==SEMICOLON) {
        sentences.add(tokens.subList(i,j+1));
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
      PRINTLN, new FunctionCallStrategy()
    );
  }


}
