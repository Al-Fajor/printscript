package org.example.languageconfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.example.ResolvedType;
import org.example.ast.BinaryOperator;
import org.json.JSONArray;
import org.json.JSONObject;

public class Language {
	String content;

	{
		try (InputStream inputStream =
						getClass().getClassLoader().getResourceAsStream("lang.json");
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			content = sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(
					"Could not load PrintScript's language configuration; got error:\n" + e);
		}
	}

	JSONObject json = new JSONObject(content);

	Map<OperationType, ResolvedType> binaryOperations;

	{
		binaryOperations = new HashMap<>();

		JSONArray jsonArray = json.getJSONArray("binaryOperations");
		for (int i = 0; i < jsonArray.length(); i++) {
			String operation = jsonArray.getString(i);
			String[] parts = operation.split(" ");
			binaryOperations.put(
					new OperationType(
							parseType(parts[0]), parseOperator(parts[1]), parseType(parts[2])),
					parseType(parts[4]));
		}
	}

	private BinaryOperator parseOperator(String operator) {
		return switch (operator) {
			case "+" -> BinaryOperator.SUM;
			case "-" -> BinaryOperator.SUBTRACTION;
			case "/" -> BinaryOperator.DIVISION;
			case "*" -> BinaryOperator.MULTIPLICATION;
			default -> throw new IllegalStateException("Operator not supported: " + operator);
		};
	}

	private ResolvedType parseType(String type) {
		return switch (type) {
			case "string" -> ResolvedType.STRING;
			case "number" -> ResolvedType.NUMBER;
				case "boolean" -> ResolvedType.BOOLEAN;
			case "void" -> ResolvedType.VOID;
			case "any" -> ResolvedType.WILDCARD;
			default -> throw new IllegalStateException("Type not supported: " + type);
		};
	}

	public Language() {}

	public boolean isOperationSupported(
			ResolvedType type1, BinaryOperator operator, ResolvedType type2) {
		return binaryOperations.containsKey(new OperationType(type1, operator, type2));
	}

	public ResolvedType getResolvedType(
			ResolvedType type1, BinaryOperator operator, ResolvedType type2) {
		return binaryOperations.get(new OperationType(type1, operator, type2));
	}
}
