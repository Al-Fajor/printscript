package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.json.JSONArray;
import org.json.JSONObject;

public class AstBuilder {
	public List<AstComponent> buildFromJson(String filePath) throws IOException {
		File file = new File(filePath);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		JSONObject json = new JSONObject(content);
		JSONArray astArray = json.getJSONArray("ast_list");

		List<AstComponent> result = new ArrayList<>();

		for (int i = 0; i < astArray.length(); i++) {
			JSONObject jsonObject = astArray.getJSONObject(i);

			String rootComponentName = jsonObject.keys().next();
			JSONObject rootComponent = jsonObject.getJSONObject(rootComponentName);

			result.add(mapToAstComponent(rootComponent, rootComponentName));
		}

		return result;
	}

	private AstComponent mapToAstComponent(
			JSONObject astComponentJson, String astComponentJsonName) {
		astComponentJsonName = deleteNumbersInName(astComponentJsonName);
		switch (astComponentJsonName) {
			case "assignation":
				Iterator<String> subComponentNames = astComponentJson.keys();
				String firstComponentName = subComponentNames.next();
				JSONObject firstComponent = astComponentJson.getJSONObject(firstComponentName);
				String secondComponentName = subComponentNames.next();
				JSONObject secondComponent = astComponentJson.getJSONObject(secondComponentName);

				if (subComponentNames.hasNext()) {
					throw new IllegalArgumentException(
							"Cannot parse JSON: Received an assignation with too many parameters");
				}

				return new AssignationStatement(
						(IdentifierComponent) mapToAstComponent(firstComponent, firstComponentName),
						(EvaluableComponent)
								mapToAstComponent(secondComponent, secondComponentName));
			case "declaration":
				return new Declaration(
						mapToDeclarationType(astComponentJson.getString("declarationType")),
						astComponentJson.getString("name"));
			case "literal":
				Object value = astComponentJson.get("value");
				if (value instanceof String) {
					return new Literal<>((String) value);
				} else if (value instanceof Number) {
					return new Literal<>((Number) value);
				} else if (value == JSONObject.NULL) {
					return new Literal<>(null);
				} else
					throw new IllegalArgumentException(
							"Cannot parse JSON: Unsupported value " + value + " for literal");
			case "identifier":
				return new Identifier(
						astComponentJson.getString("name"),
						mapToIdentifierType(astComponentJson.getString("identifierType")));
			case "binaryExpression":
				Iterator<String> operandNames = astComponentJson.keys();
				operandNames.next();

				String firstOperandName = operandNames.next();
				JSONObject firstOperand = astComponentJson.getJSONObject(firstOperandName);
				String secondOperandName = operandNames.next();
				JSONObject secondOperand = astComponentJson.getJSONObject(secondOperandName);

				return new BinaryExpression(
						mapToOperator(astComponentJson.getString("op")),
						(EvaluableComponent) mapToAstComponent(firstOperand, firstOperandName),
						(EvaluableComponent) mapToAstComponent(secondOperand, secondOperandName));
			case "functionCall":
				return new FunctionCallStatement(
						(IdentifierComponent)
								mapToAstComponent(
										astComponentJson.getJSONObject("identifier"), "identifier"),
						(Parameters)
								mapToAstComponent(
										astComponentJson.getJSONObject("params"), "params"));
			case "params":
				List<EvaluableComponent> parameters = new ArrayList<>();
				for (String key : astComponentJson.keySet()) {
					parameters.add(
							(EvaluableComponent)
									mapToAstComponent(astComponentJson.getJSONObject(key), key));
				}

				return new Parameters(parameters);
			case "conditional", "if", "ifClauses", "statementBlock":
				throw new RuntimeException(
						"Not implemented yet: case '" + astComponentJsonName + "'");
			default:
				throw new IllegalArgumentException(
						astComponentJsonName + " is not a valid ast component");
		}
	}

	private BinaryOperator mapToOperator(String op) {
		return switch (op) {
			case "+" -> BinaryOperator.SUM;
			case "-" -> BinaryOperator.SUBTRACTION;
			case "/" -> BinaryOperator.DIVISION;
			case "*" -> BinaryOperator.MULTIPLICATION;
			default -> throw new IllegalArgumentException("Invalid operator: " + op);
		};
	}

	private static String deleteNumbersInName(String astComponentName) {
		String[] split = astComponentName.split("_");
		return split[split.length - 1];
	}

	private IdentifierType mapToIdentifierType(String identifierType) {
		return switch (identifierType) {
			case "variable" -> IdentifierType.VARIABLE;
			case "function" -> IdentifierType.FUNCTION;
			default ->
					throw new IllegalArgumentException("Invalid identifierType: " + identifierType);
		};
	}

	private DeclarationType mapToDeclarationType(String declarationType) {
		return switch (declarationType) {
			case "String" -> DeclarationType.STRING;
			case "Number" -> DeclarationType.NUMBER;
			default ->
					throw new IllegalArgumentException(
							"Invalid declarationType " + declarationType);
		};
	}
}
