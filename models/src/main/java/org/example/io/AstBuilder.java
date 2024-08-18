package org.example.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
			Object rootComponent = jsonObject.get(rootComponentName);

			result.add(mapToAstComponent(rootComponent, rootComponentName));
		}

		return result;
	}

	private AstComponent mapToAstComponent(
			JSONObject astComponentJson, String astComponentJsonName) {
		switch (astComponentJsonName) {
            case "declaration":
				return new Declaration(
						mapToDeclarationType(astComponentJson.getString("declarationType")),
						astComponentJson.getString("name"));
			case "literal":
				Object value = astComponentJson.get("value");
				return switch (value) {
					case String ignored -> new Literal<>((String) value);
					case Number ignored -> new Literal<>((Number) value);
					case null -> new Literal<>(null);
					default ->
							throw new IllegalArgumentException(
									"Cannot parse JSON: Unsupported value "
											+ value
											+ " for literal");
				};
			case "identifier":
				return new Identifier(
						astComponentJson.getString("name"),
						mapToIdentifierType(astComponentJson.getString("identifierType")));
			case "functionCall":
				return new FunctionCallStatement(
						(IdentifierComponent)
								mapToAstComponent(
										astComponentJson.getJSONObject("identifier"), "identifier"),
						(Parameters)
								mapToAstComponent(
										astComponentJson.getJSONArray("params"), "params"));
			case "conditional", "if", "ifClauses", "statementBlock":
				throw new RuntimeException(
						"Not implemented yet: case '" + astComponentJsonName + "'");
			default:
				throw new IllegalArgumentException(
						astComponentJsonName + " is not a valid ast component");
		}
	}

	private AstComponent mapToAstComponent(JSONArray jsonArray, String astComponentJsonName) {
		switch (astComponentJsonName) {
			case "binaryExpression":
				String firstOperandName = jsonArray.getJSONObject(1).keys().next();
				Object firstOperand = jsonArray.getJSONObject(1).get(firstOperandName);
				String secondOperandName = jsonArray.getJSONObject(2).keys().next();
				Object secondOperand = jsonArray.getJSONObject(2).get(secondOperandName);

				return new BinaryExpression(
						mapToOperator(jsonArray.getJSONObject(0).getString("op")),
						(EvaluableComponent) mapToAstComponent(firstOperand, firstOperandName),
						(EvaluableComponent) mapToAstComponent(secondOperand, secondOperandName));

			case "assignation":
				String firstComponentName = jsonArray.getJSONObject(0).keys().next();
				JSONObject firstComponent =
						jsonArray.getJSONObject(0).getJSONObject(firstComponentName);
				String secondComponentName = jsonArray.getJSONObject(1).keys().next();
				Object secondComponent = jsonArray.getJSONObject(1).get(secondComponentName);

				return new AssignationStatement(
						(IdentifierComponent) mapToAstComponent(firstComponent, firstComponentName),
						(EvaluableComponent)
								mapToAstComponent(secondComponent, secondComponentName));

			case "params":
				List<EvaluableComponent> parameters = new ArrayList<>();
				for (Object object : jsonArray) {
					JSONObject jsonObject = (JSONObject) object;
					String key = jsonObject.keys().next();
					Object subComponent = jsonObject.get(key);

					parameters.add((EvaluableComponent) mapToAstComponent(subComponent, key));
				}

				return new Parameters(parameters);

			default:
				throw new IllegalArgumentException(
						astComponentJsonName + " is not a valid ast component");
		}
	}

	private AstComponent mapToAstComponent(Object object, String objectName) {
		if (object instanceof JSONObject jsonObject) {
			return mapToAstComponent(jsonObject, objectName);
		}

		if (object instanceof JSONArray jsonArray) {
			return mapToAstComponent(jsonArray, objectName);
		} else {
			throw new IllegalArgumentException("Can only map JSON objects and JSON arrays");
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
