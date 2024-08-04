package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AstBuilder {
    public static void main(String[] args) throws IOException {
        System.out.println(
                new AstBuilder().buildFromJson("C:\\Users\\tomas\\projects\\ingsis\\src\\test\\java\\model\\assignationExample.json")
        );
    }

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

    private AstComponent mapToAstComponent(JSONObject astComponent, String astComponentName) {
        switch (astComponentName) {
            case "assignation":
                Iterator<String> subComponentNames = astComponent.keys();
                String firstComponentName = subComponentNames.next();
                JSONObject firstComponent = astComponent.getJSONObject(firstComponentName);
                String secondComponentName = subComponentNames.next();
                JSONObject secondComponent = astComponent.getJSONObject(secondComponentName);

                if (subComponentNames.hasNext()) {
                    throw new IllegalArgumentException("Cannot parse JSON: Received an assignation with too many parameters");
                }

                return new Assignation(
                        mapToAstComponent(firstComponent, firstComponentName),
                        mapToAstComponent(secondComponent, secondComponentName)
                );
            case "declaration":
                return new Declaration(
                        mapToDeclarationType(astComponent.getString("declarationType")),
                        astComponent.getString("name")
                );
            case "literal":
                Object value = astComponent.get("value");
                if (value instanceof String) {
                    return new Literal<String>((String) value);
                }
                else if (value instanceof Integer) {
                    return new Literal<Integer>((Integer) value);
                }
                else throw new IllegalArgumentException("Cannot parse JSON: Unsupported value " + value + " for literal");
            default:
                throw new IllegalArgumentException(astComponentName + " is not a valid ast component");
        }
    }

    private DeclarationType mapToDeclarationType(String declarationType) {
        switch (declarationType) {
            case "String": return DeclarationType.STRING;
            case "Num": return DeclarationType.NUMBER;
            default: throw new IllegalArgumentException("Invalid declarationType " + declarationType);
        }
    }
}
