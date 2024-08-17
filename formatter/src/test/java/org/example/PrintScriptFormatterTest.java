package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;



class PrintScriptFormatterTest {
    private final AstBuilder astBuilder = new AstBuilder();
    private final FileParser fileParser = new FileParser();
    @Test
    void variableAssignation() throws IOException {
        String path = "src/test/resources/variable_assignation.json";
        PrintScriptFormatter formatter = new PrintScriptFormatter();
        String code = formatter.format(astBuilder.buildFromJson(path));
        assertEquals(code, fileParser.getCode(path));
    }

    @Test
    void stringAssignation() throws IOException {
        String path = "src/test/resources/string_assignation.json";
        PrintScriptFormatter formatter = new PrintScriptFormatter();
        String code = formatter.format(astBuilder.buildFromJson(path));
        assertEquals(code, fileParser.getCode(path));
    }

    @Test
    void printOperation() throws IOException {
        String path = "src/test/resources/print_operation.json";
        PrintScriptFormatter formatter = new PrintScriptFormatter();
        String code = formatter.format(astBuilder.buildFromJson(path));
        assertEquals(code, fileParser.getCode(path));
    }

    @Test
    void variableAssignation2() throws IOException {
        String path = "src/test/resources/variable_assignation2.json";
        PrintScriptFormatter formatter = new PrintScriptFormatter();
        String code = formatter.format(astBuilder.buildFromJson(path));
        assertEquals(code, fileParser.getCode(path));
    }

}