package org.example;

import org.example.token.BaseTokenTypes;
import org.example.token.Token;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileParser {

  //Need to define the correct format of expected results, should be a JSON
  public List<Token> getTokens(String filePath) throws IOException {
    //Change to a method to correctly get all the code
    File file = new File(filePath);
    String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
    JSONObject json = new JSONObject(content);
    String tokenString = json.get("tokens").toString();

    String ARROW = "->";
    List<String> tempTokenList = Arrays.stream(tokenString.split(ARROW)).map(String::strip).toList();

    if(tempTokenList.size() == 1) return List.of();

    return tempTokenList.stream().map(this::getToken).map(pair -> new Token(pair.first, 0, 0, pair.second)).collect(Collectors.toList());
  }

  private Pair<BaseTokenTypes, String> getToken(String token) {
    if(token.indexOf('(') == -1){
      return new Pair<>(BaseTokenTypes.valueOf(token), "");
    }
    String tokenName = token.substring(0, token.indexOf('('));
    String tokenValue = token.substring(token.indexOf('(') + 1, token.lastIndexOf(')'));
    return new Pair<>(BaseTokenTypes.valueOf(tokenName), tokenValue);

  }


  private record Pair<T, W>(T first, W second){}


}
