package org.example;

<<<<<<<< HEAD:syntax/src/test/java/org/example/SyntaxTestBuilder.java
import model.AstBuilder;
import model.AstComponent;
import model.FileParser;
import model.Token;
import parser.syntax.analyzer.SyntaxAnalyzerImpl;
import parser.syntax.result.SyntaxResult;
========
import org.example.ast.AstComponent;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;
import org.example.token.Token;
>>>>>>>> 6035302c02e585b433224c294c5bf11a88e57129:syntax/src/test/java/org/example/TestBuilder.java

import java.io.IOException;
import java.util.List;

public class SyntaxTestBuilder {
  private List<AstComponent> getASTFromJSON(String filePath) throws IOException {
    return new AstBuilder().buildFromJson(filePath);
  }

  public boolean testSyntax(String filePath) throws IOException {
    FileParser parser = new FileParser();
    List<Token> tokens = parser.getTokens(filePath);

    SyntaxResult result = new SyntaxAnalyzerImpl().analyze(tokens);
    List<AstComponent> expectedList = getASTFromJSON(filePath);

    if(result.isFailure()) {
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
