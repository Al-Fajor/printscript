package parser.syntax;

import model.BaseTokenTypes;
import model.Token;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileParser {

  private final String ARROW = "->";
  private final String TOKENS = "Tokens:";
  private final String EXPECTED = "Expected:";

  //Need to define the correct format of expected results, should be a JSON
  public List<Token> getTokens(String filePath) throws IOException {
    //Change to a method to correctly get all the code
    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    String tokenString = reader.readLine();

    if(tokenString.startsWith(TOKENS)) {
      tokenString = tokenString.substring(TOKENS.length());
    }

    List<String> tempTokenList = Arrays.stream(tokenString.split(ARROW)).toList();

    if(tempTokenList.size() == 1) return List.of();

    //Post-modification
    tempTokenList = tempTokenList.stream().map(String::strip).toList();


    List<Token> finalTokenList = new ArrayList<>();

    for (String token : tempTokenList) {
      Pair<BaseTokenTypes, String> pair = getTokenType(token);
      finalTokenList.add(new Token(pair.first, 0, 0, pair.second));
    }
    return finalTokenList;
  }

  public JSONObject getJSONFromFile(String filePath){
    StringBuilder jsonContent = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      // Skip the first line
      br.readLine();

      String line;
      while ((line = br.readLine()) != null) {
        if(line.startsWith(EXPECTED)){
          jsonContent.append(line.substring(EXPECTED.length()));
          continue;
        }
        jsonContent.append(line);
      }

      return new JSONObject(jsonContent.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Pair<BaseTokenTypes, String> getTokenType(String token) {
    if(token.lastIndexOf('(') == -1){
      return new Pair<>(BaseTokenTypes.valueOf(token), "");
    }
    String tokenName = token.substring(0, token.lastIndexOf('('));
    String tokenValue = token.substring(token.lastIndexOf('(') + 1, token.lastIndexOf(')'));

    System.out.println(tokenName);
    System.out.println(tokenValue);

    return new Pair<>(BaseTokenTypes.valueOf(tokenName), tokenValue);

  }


  private record Pair<T, W>(T first, W second){}


}
