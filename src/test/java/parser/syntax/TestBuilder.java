package parser.syntax;

import model.AstComponent;
import model.Token;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class TestBuilder {
  public JSONObject getJSONFromAst(List<AstComponent> astList){
    //TODO
    JSONObject obj = new JSONObject();
    return obj;
  }

  public boolean testSyntax(String filePath, ExpectedResult expectedResult) throws IOException {
    FileParser parser = new FileParser();
    List<Token> tokens = parser.getTokens(filePath);
    List<AstComponent> astList = new SyntaxAnalyzerImpl().analyze(tokens);

    JSONObject expectedJson = parser.getJSONFromFile(filePath);
    JSONObject actualJson = getJSONFromAst(astList);

    return expectedResult == compareJsons(expectedJson, actualJson);
  }

  private ExpectedResult compareJsons(JSONObject expectedJson, JSONObject actualJson) {
    if(expectedJson == null || actualJson == null){
      return ExpectedResult.ERROR;
    }
    return expectedJson.similar(actualJson) ? ExpectedResult.SUCCESS : ExpectedResult.FAILURE;
  }
}
