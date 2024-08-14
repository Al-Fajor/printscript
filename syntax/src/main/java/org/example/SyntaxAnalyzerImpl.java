package org.example;

import org.example.ast.AstComponent;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;
import org.example.result.SyntaxSuccess;
import org.example.sentence.builder.SentenceBuilder;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SyntaxAnalyzerImpl implements SyntaxAnalyzer{
  @Override
  public SyntaxResult analyze(List<Token> tokens) {
    if(tokens.isEmpty()) return new SyntaxSuccess(List.of());
    return buildSentences(tokens);
  }

  private SyntaxResult buildSentences(List<Token> tokens) {
    try{
      List<List<Token>> tokenSentences = getSentencesWithTokens(tokens);
      List<AstComponent> components = tokenSentences.stream().map(this::buildSentence)
        .collect(Collectors.toList());

      return getSyntaxResult(components, tokenSentences);

    } catch (NullPointerException e){
      return new SyntaxError("Invalid tokens");
    }
  }

  private SyntaxResult getSyntaxResult(List<AstComponent> components, List<List<Token>> tokenSentences) {
    return components.contains(null) ?
      new SyntaxError("Invalid sentence at index: " + components.indexOf(null) + ";\n" +
        " Starting token: " + tokenSentences.get(components.indexOf(null)).getFirst()) :
      new SyntaxSuccess(components);
  }

  private AstComponent buildSentence(List<Token> sentence) {
    SentenceBuilder builder = new SentenceBuilder();
    return builder.buildSentence(sentence);
  }

  private List<List<Token>> getSentencesWithTokens(List<Token> tokens) {
    List<List<Token>> sentences = new ArrayList<>();
    int i = 0;
    for (int j = 0; j < tokens.size() ; j++) {
      if(tokens.get(j).getType() == BaseTokenTypes.SEMICOLON) {
        sentences.add(tokens.subList(i,j+1));
        i=j+1;
        if(i>=tokens.size()) break;
      }
    }
    return sentences;
  }

//  private Map<TokenType, Integer> bindingPowers(){
//
//  }


}
