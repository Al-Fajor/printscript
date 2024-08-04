package parser.syntax;

import model.AstComponent;
import model.Token;

import java.util.ArrayList;
import java.util.List;

public class SyntaxAnalyzerImpl implements SyntaxAnalyzer{
  @Override
  public List<AstComponent> analyze(List<Token> tokens) {
    //TODO

    List<AstComponent> translation = getComponents(tokens);
    return List.of();
  }

  private List<AstComponent> getComponents(List<Token> tokens) {
    //TODO
    List<AstComponent> list = new ArrayList<>();
    for (Token token : tokens) {

    }
    return list;
  }


}
