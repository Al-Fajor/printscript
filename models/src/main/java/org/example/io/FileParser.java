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
		JSONObject json = getJsonObject(filePath);
		return json.get("code").toString();
	}

	// Need to define the correct format of expected results, should be a JSON
	public List<Token> getTokens(String filePath) throws IOException {

		JSONObject json = getJsonObject(filePath);
		String tokenString = json.get("tokens").toString();

		List<String> tempTokenList =
				Arrays.stream(tokenString.split("->")).map(String::strip).toList();

		if (tempTokenList.size() == 1) return List.of();

		return tempTokenList.stream()
				.map(this::getToken)
				//                TODO use actual positions.
				.map(
						pair ->
								new Token(
										pair.first(),
										new Pair<>(0, 0),
										new Pair<>(0, 0),
										pair.second()))
				.collect(Collectors.toList());
	}

	// Private

	private Pair<BaseTokenTypes, String> getToken(String token) {
		if (token.indexOf('(') == -1) {
			return new Pair<>(BaseTokenTypes.valueOf(token), "");
		}
		String tokenName = token.substring(0, token.indexOf('('));
		String tokenValue = token.substring(token.indexOf('(') + 1, token.lastIndexOf(')'));
		return new Pair<>(BaseTokenTypes.valueOf(tokenName), tokenValue);
	}

	private JSONObject getJsonObject(String filePath) throws IOException {
		File file = new File(filePath);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		return new JSONObject(content);
	}
}
