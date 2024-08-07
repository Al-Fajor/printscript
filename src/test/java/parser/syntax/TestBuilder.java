package parser.syntax;

import models.ASTBuilder;
import model.AstComponent;
import model.Token;

import java.io.IOException;
import java.util.List;

public class TestBuilder {
  private List<AstComponent> getASTFromJSON(String filePath) throws IOException {
    return new ASTBuilder().buildFromJson(filePath);
  }

  public boolean testSyntax(String filePath) throws IOException {
    FileParser parser = new FileParser();
    List<Token> tokens = parser.getTokens(filePath);

    List<AstComponent> actualList = new SyntaxAnalyzerImpl().analyze(tokens);
    List<AstComponent> expectedList = getASTFromJSON(filePath);


    return compareASTs(expectedList, actualList);
  }

  private boolean compareASTs(List<AstComponent> expectedList, List<AstComponent> actualList) {
    return equalLists(expectedList, actualList);
  }

  private boolean equalLists(List<AstComponent> expectedList, List<AstComponent> actualList) {
    for(int i = 0; i < expectedList.size(); i++){
      if(!expectedList.get(i).equals(actualList.get(i))){
        System.out.println("Expected: " + printWholeList(expectedList));
        System.out.println("Actual: " + printWholeList(actualList));
        return false;
      }
    }
    return true;
  }

  private String printWholeList(List<AstComponent> expectedList) {
    StringBuilder builder = new StringBuilder();
    for (AstComponent astComponent : expectedList) {
      builder.append(astComponent);
    }
    return builder.toString();

  }
}
