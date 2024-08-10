package org.example;

import model.AstBuilder;
import model.AstComponent;
import model.Token;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;

import java.io.IOException;
import java.util.List;

public class TestBuilder {
  private List<AstComponent> getASTFromJSON(String filePath) throws IOException {
    return new AstBuilder().buildFromJson(filePath);
  }

  public boolean testSyntax(String filePath) throws IOException {
    FileParser parser = new FileParser();
    List<Token> tokens = parser.getTokens(filePath);

    SyntaxResult result = new SyntaxAnalyzerImpl().analyze(tokens);
    List<AstComponent> expectedList = getASTFromJSON(filePath);

    if(result instanceof SyntaxError) {
      return expectedList.isEmpty();
    }

    List<AstComponent> actualList = result.getComponents();

    return compareASTs(expectedList, actualList);
  }

  private boolean compareASTs(List<AstComponent> expectedList, List<AstComponent> actualList) {
    return equalLists(expectedList, actualList);
  }

  private boolean equalLists(List<AstComponent> expectedList, List<AstComponent> actualList) {
      if(expectedList.size() != actualList.size()) return error(expectedList, actualList);
      for(int i = 0; i < expectedList.size(); i++){
        if(!expectedList.get(i).equals(actualList.get(i))){
          return error(expectedList, actualList);
        }
      }
      return true;


  }

  private boolean error(List<AstComponent> expectedList, List<AstComponent> actualList){
    System.out.println("Expected: " + printWholeList(expectedList));
    System.out.println("Actual: " + printWholeList(actualList));
    return false;
  }


  private String printWholeList(List<AstComponent> expectedList) {
    StringBuilder builder = new StringBuilder();
    for (AstComponent astComponent : expectedList) {
      builder.append(astComponent);
    }
    return builder.toString();

  }
}
