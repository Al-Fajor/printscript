package org.example;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterTestFramework {

    public void testRules(String path) throws IOException {
        AstBuilder astBuilder = new AstBuilder();
        FileParser fileParser = new FileParser();
        TestRuleMapFactory testRuleMapFactory = new TestRuleMapFactory(path + "/formatter.rules.json");
        Formatter formatter = new PrintScriptFormatter(testRuleMapFactory);
//        TODO get all non rule files from directory instead of using this array
        String[] cases = new String[]{
                "variable_assignation",
                "string_assignation",
                "print_operation",
                "variable_assignation2",
                "string_concatenation",
                "print_variables"
        };
        for (String testCase : cases) {
            String jsonPath = path + "/" + testCase + ".json";
            System.out.println(jsonPath);
            String code = formatter.format(astBuilder.buildFromJson(jsonPath));
            assertEquals(fileParser.getCode(jsonPath), code);
        }
    }
}
