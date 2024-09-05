package org.example.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.example.Pair;
import org.example.ast.*;
import org.example.ast.statement.AssignmentStatement;
import org.example.ast.statement.DeclarationAssignmentStatement;
import org.example.ast.statement.DeclarationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

public class AstBuilder {

	public static final Pair<Integer, Integer> PLACEHOLDER = new Pair<>(1, 1);

	public List<Statement> buildFromJson(String filePath) throws IOException {
		File file = new File(filePath);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		JSONObject json = new JSONObject(content);
		JSONArray astArray = json.getJSONArray("ast_list");

		List<Statement> result = new ArrayList<>();

		for (int i = 0; i < astArray.length(); i++) {
			JSONObject jsonObject = astArray.getJSONObject(i);

			String rootComponentName = jsonObject.keys().next();
			Object rootComponent = jsonObject.get(rootComponentName);

			result.add(mapToStatement(rootComponent, rootComponentName));
		}

		return result;
	}

	private Statement mapToStatement(JSONObject astComponentJson, String astComponentJsonName) {
		return switch (astComponentJsonName) {
			case "functionCall" ->
					new FunctionCallStatement(
							(Identifier)
									mapToAstComponent(
											astComponentJson.getJSONObject("identifier"),
											"identifier"),
							(Parameters)
									mapToAstComponent(
											astComponentJson.getJSONArray("params"), "params"),
							PLACEHOLDER,
							PLACEHOLDER);
			case "conditional", "if", "ifClauses", "statementBlock" ->
					throw new RuntimeException(
							"Not implemented yet: case '" + astComponentJsonName + "'");
			default ->
					throw new IllegalArgumentException(
							astComponentJsonName + " is not a valid statement");
		};
	}

	private AstComponent mapToAstComponent(
			JSONObject astComponentJson, String astComponentJsonName) {
		return switch (astComponentJsonName) {
			case "literal" -> {
				Object value = astComponentJson.get("value");

				// This condition seems stupid, but it is how the Null object
				// is implemented in org.json
				if (value.equals(null)) yield new Literal<>(null, PLACEHOLDER, PLACEHOLDER);

				yield switch (value) {
					case String ignored -> new Literal<>((String) value, PLACEHOLDER, PLACEHOLDER);
					case Number ignored -> new Literal<>((Number) value, PLACEHOLDER, PLACEHOLDER);
					default ->
							throw new IllegalArgumentException(
									"Cannot parse JSON: Unsupported value "
											+ value
											+ " for literal");
				};
			}
			case "identifier" ->
					new Identifier(astComponentJson.getString("name"), PLACEHOLDER, PLACEHOLDER);
			case "declaration" -> null;

			case "conditional", "if", "ifClauses", "statementBlock" ->
					throw new RuntimeException(
							"Not implemented yet: case '" + astComponentJsonName + "'");
			default ->
					throw new IllegalArgumentException(
							astComponentJsonName + " is not a valid ast component");
		};
	}

	private Statement mapToStatement(JSONArray jsonArray, String astComponentJsonName) {
		switch (astComponentJsonName) {
			case "assignation":
				String firstComponentName = jsonArray.getJSONObject(0).keys().next();
				JSONObject firstComponent =
						jsonArray.getJSONObject(0).getJSONObject(firstComponentName);
				String secondComponentName = jsonArray.getJSONObject(1).keys().next();
				Object secondComponent = jsonArray.getJSONObject(1).get(secondComponentName);

				AstComponent mappedFirstComponent =
						mapToAstComponent(firstComponent, firstComponentName);
				EvaluableComponent mappedSecondComponent =
						(EvaluableComponent) mapToAstComponent(secondComponent, firstComponentName);

				if (mappedFirstComponent instanceof Identifier) {

					return new AssignmentStatement(
							(Identifier) mappedFirstComponent,
							mappedSecondComponent,
							PLACEHOLDER,
							PLACEHOLDER);
				}

				if (secondComponent instanceof Literal<?>
						&& ((Literal<?>) secondComponent).getValue() == null) {
					JSONObject subObject = jsonArray.getJSONObject(0).getJSONObject("declaration");

					return new DeclarationStatement(
							mapToDeclarationType(subObject.getString("declarationType")),
							IdentifierType.VARIABLE,
							new Identifier(subObject.getString("name"), PLACEHOLDER, PLACEHOLDER),
                            PLACEHOLDER,
                            PLACEHOLDER
                    );
				}

				JSONObject subObject = jsonArray.getJSONObject(0).getJSONObject("declaration");
				return new DeclarationAssignmentStatement(
						mapToDeclarationType(subObject.getString("declarationType")),
						IdentifierType.VARIABLE,
						new Identifier(subObject.getString("name"), PLACEHOLDER, PLACEHOLDER),
						(EvaluableComponent) secondComponent,
						PLACEHOLDER,
						PLACEHOLDER);

			default:
				throw new IllegalArgumentException(
						astComponentJsonName + " is not a valid ast statement");
		}
	}

	private AstComponent mapToAstComponent(JSONArray jsonArray, String astComponentJsonName) {
		switch (astComponentJsonName) {
			case "params":
				List<EvaluableComponent> parameters = new ArrayList<>();
				for (Object object : jsonArray) {
					JSONObject jsonObject = (JSONObject) object;
					String key = jsonObject.keys().next();
					Object subComponent = jsonObject.get(key);

					parameters.add((EvaluableComponent) mapToAstComponent(subComponent, key));
				}

				return new Parameters(parameters, PLACEHOLDER, PLACEHOLDER);

			case "binaryExpression":
				String firstOperandName = jsonArray.getJSONObject(1).keys().next();
				Object firstOperand = jsonArray.getJSONObject(1).get(firstOperandName);
				String secondOperandName = jsonArray.getJSONObject(2).keys().next();
				Object secondOperand = jsonArray.getJSONObject(2).get(secondOperandName);

				return new BinaryExpression(
						mapToOperator(jsonArray.getJSONObject(0).getString("op")),
						(EvaluableComponent) mapToAstComponent(firstOperand, firstOperandName),
						(EvaluableComponent) mapToAstComponent(secondOperand, secondOperandName),
						PLACEHOLDER,
						PLACEHOLDER);

			default:
				throw new IllegalArgumentException(
						astComponentJsonName + " is not a valid ast component");
		}
	}

	private Statement mapToStatement(Object object, String objectName) {
		if (object instanceof JSONObject jsonObject) {
			return mapToStatement(jsonObject, objectName);
		}

		if (object instanceof JSONArray jsonArray) {
			return mapToStatement(jsonArray, objectName);
		} else {
			throw new IllegalArgumentException("Can only map JSON objects and JSON arrays");
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
