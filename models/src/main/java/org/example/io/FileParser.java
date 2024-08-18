package org.example.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.example.Pair;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;
import org.json.JSONObject;

public class FileParser {

	public String getCode(String filePath) throws IOException {
		File file = new File(filePath);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		JSONObject json = new JSONObject(content);
		return json.get("code").toString();
	}

	// Need to define the correct format of expected results, should be a JSON
	public List<Token> getTokens(String filePath) throws IOException {
		// Change to a method to correctly get all the code
		File file = new File(filePath);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		JSONObject json = new JSONObject(content);
		String tokenString = json.get("tokens").toString();

		String arrow = "->";
		List<String> tempTokenList =
				Arrays.stream(tokenString.split(arrow)).map(String::strip).toList();

		if (tempTokenList.size() == 1) return List.of();

		return tempTokenList.stream()
				.map(this::getToken)
				.map(pair -> new Token(pair.first(), 0, 0, pair.second()))
				.collect(Collectors.toList());
	}

	private Pair<BaseTokenTypes, String> getToken(String token) {
		if (token.indexOf('(') == -1) {
			return new Pair<>(BaseTokenTypes.valueOf(token), "");
		}
		String tokenName = token.substring(0, token.indexOf('('));
		String tokenValue = token.substring(token.indexOf('(') + 1, token.lastIndexOf(')'));
		return new Pair<>(BaseTokenTypes.valueOf(tokenName), tokenValue);
	}
}
