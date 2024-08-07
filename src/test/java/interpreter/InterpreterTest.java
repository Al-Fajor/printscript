package interpreter;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class InterpreterTest {
    private final AstBuilder astBuilder = new AstBuilder();

    @Test
    public void testNumericVariableDeclaration() throws IOException {
        Interpreter interpreter = new PrintScriptInterpreter();
        interpreter.interpret(astBuilder.buildFromJson("src/test/resources/interpreter_test_cases/declare_numeric_variable.json"));
    }

    @Test
    public void testStringVariableDeclaration() throws IOException {
        Interpreter interpreter = new PrintScriptInterpreter();
        interpreter.interpret(astBuilder.buildFromJson("src/test/resources/interpreter_test_cases/declare_string_variable.json"));
    }

    @Test
    public void testAssignNumericVariable() throws IOException {
        Interpreter interpreter = new PrintScriptInterpreter();
        interpreter.interpret(astBuilder.buildFromJson("src/test/resources/interpreter_test_cases/assign_numeric_variable.json"));
    }

    @Test
    public void testAssignStringVariable() throws IOException {
        Interpreter interpreter = new PrintScriptInterpreter();
        interpreter.interpret(astBuilder.buildFromJson("src/test/resources/interpreter_test_cases/assign_string_variable.json"));
    }

    @Test
    public void testNumberOperations() throws IOException {
        Interpreter interpreter = new PrintScriptInterpreter();
        interpreter.interpret(astBuilder.buildFromJson("src/test/resources/interpreter_test_cases/number_operations.json"));
    }

    @Test
    public void testStringConcatenation() throws IOException {
        Interpreter interpreter = new PrintScriptInterpreter();
        interpreter.interpret(astBuilder.buildFromJson("src/test/resources/interpreter_test_cases/string_concatenation.json"));
    }

    @Test
    public void testPrint() throws IOException {
        Interpreter interpreter = new PrintScriptInterpreter();
        interpreter.interpret(astBuilder.buildFromJson("src/test/resources/interpreter_test_cases/print_variables.json"));
    }
}
