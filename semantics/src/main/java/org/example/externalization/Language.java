package org.example.externalization;

import org.example.ast.BinaryOperator;
import org.example.ast.DeclarationType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Language {
    Path resourcePath = Paths.get("src/main/resources/lang.json");
    File file = resourcePath.toFile();
    String content;

    {
        try {
            content = new String(Files.readAllBytes(Paths.get(file.toURI())));
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not load PrintScript's language configuration; got error:\n" + e.getMessage()
            );
        }
    }

    JSONObject json = new JSONObject(content);

    Map<OperationType, DeclarationType> binaryOperations;
    {
        binaryOperations = new HashMap<>();

        JSONArray jsonArray = json.getJSONArray("binaryOperations");
        for (int i = 0; i < jsonArray.length(); i++) {
            String operation = jsonArray.getString(i);
            String[] parts = operation.split(" ");
            binaryOperations.put(
                    new OperationType(
                            parseType(parts[0]),
                            parseOperator(parts[1]),
                            parseType(parts[2])
                    ),
                    parseType(parts[4])
            );
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

    private DeclarationType parseType(String type) {
            return switch (type) {
                case "string" -> DeclarationType.STRING;
                case "number" -> DeclarationType.NUMBER;
                default -> throw new IllegalStateException("Type not supported: " + type);
            };
    }


    public Language() {
    }

    public boolean isOperationSupported(
            DeclarationType type1,
            BinaryOperator operator,
            DeclarationType type2
    ) {
        return binaryOperations.containsKey(new OperationType(type1, operator, type2));
    }

    public DeclarationType getResolvedType(
            DeclarationType type1,
            BinaryOperator operator,
            DeclarationType type2
    ) {
        return binaryOperations.get(new OperationType(type1, operator, type2));
    }
}
