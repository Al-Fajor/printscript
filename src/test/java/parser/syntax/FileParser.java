package parser.syntax;

import model.BaseTokenTypes;
import model.Token;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    File file = new File(filePath);
    String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
    JSONObject json = new JSONObject(content);
    String tokenString = json.get("tokens").toString();

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

  private Pair<BaseTokenTypes, String> getTokenType(String token) {
    if(token.lastIndexOf('(') == -1){
      return new Pair<>(BaseTokenTypes.valueOf(token), "");
    }
    String tokenName = token.substring(0, token.lastIndexOf('('));
    String tokenValue = token.substring(token.lastIndexOf('(') + 1, token.lastIndexOf(')'));

    return new Pair<>(BaseTokenTypes.valueOf(tokenName), tokenValue);

  }


  private record Pair<T, W>(T first, W second){}


}
