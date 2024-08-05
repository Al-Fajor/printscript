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

  public boolean testSyntax(String filePath, ExpectedResult expectedResult) throws IOException {
    FileParser parser = new FileParser();
    List<Token> tokens = parser.getTokens(filePath);
    System.out.println(tokens);

    List<AstComponent> actualList = new SyntaxAnalyzerImpl().analyze(tokens);
    List<AstComponent> expectedList = getASTFromJSON(filePath);


    return expectedResult == compareASTs(expectedList, actualList);
  }

  private ExpectedResult compareASTs(List<AstComponent> expectedList, List<AstComponent> actualList) {
    if(expectedList == null || actualList == null){
      return ExpectedResult.ERROR;
    }
    return equalLists(expectedList, actualList)? ExpectedResult.SUCCESS : ExpectedResult.FAILURE;
  }

  private boolean equalLists(List<AstComponent> expectedList, List<AstComponent> actualList) {
    for(int i = 0; i < expectedList.size(); i++){
      if(!expectedList.get(i).equals(actualList.get(i))){
        return false;
      }
    }
    return true;
  }
}
