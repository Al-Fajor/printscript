package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class InterpreterTest {
	private final AstBuilder astBuilder = new AstBuilder();

	@Test
	public void testNumericVariableDeclaration() throws IOException {
		InterpreterState state = new TestState();
		Interpreter interpreter = new PrintScriptInterpreter(state);
		interpreter.interpret(
				astBuilder.buildFromJson(
						"src/test/resources/test_cases/declare_numeric_variable.json"));
		variableExists(state, "num", (Double) null);
	}

	@Test
	public void testStringVariableDeclaration() throws IOException {
		InterpreterState state = new TestState();
		Interpreter interpreter = new PrintScriptInterpreter(state);
		interpreter.interpret(
				astBuilder.buildFromJson(
						"src/test/resources/test_cases/declare_string_variable.json"));
		variableExists(state, "string", (String) null);
	}

	@Test
	public void testAssignNumericVariable() throws IOException {
		InterpreterState state = new TestState();
		Interpreter interpreter = new PrintScriptInterpreter(state);
		interpreter.interpret(
				astBuilder.buildFromJson(
						"src/test/resources/test_cases/assign_numeric_variable.json"));
		variableExists(state, "num", 7.0);
	}

	@Test
	public void testAssignStringVariable() throws IOException {
		InterpreterState state = new TestState();
		Interpreter interpreter = new PrintScriptInterpreter(state);
		interpreter.interpret(
				astBuilder.buildFromJson(
						"src/test/resources/test_cases/assign_string_variable.json"));
		variableExists(state, "string", "This is a string");
	}

	@Test
	public void testNumberOperations() throws IOException {
		InterpreterState state = new TestState();
		Interpreter interpreter = new PrintScriptInterpreter(state);
		interpreter.interpret(
				astBuilder.buildFromJson("src/test/resources/test_cases/number_operations.json"));
		variableExists(state, "num", 46.0);
	}

	@Test
	public void testStringConcatenation() throws IOException {
		InterpreterState state = new TestState();
		Interpreter interpreter = new PrintScriptInterpreter(state);
		interpreter.interpret(
				astBuilder.buildFromJson(
						"src/test/resources/test_cases/string_concatenation.json"));
		variableExists(state, "numberString", "5.0 Cars");
		variableExists(state, "stringString", "Hello World");
	}

	@Test
	public void testPrint() throws IOException {
		setUpStreams(); // Magic to allow testing of console output
		InterpreterState state = new TestState();
		Interpreter interpreter = new PrintScriptInterpreter(state);
		interpreter.interpret(
				astBuilder.buildFromJson("src/test/resources/test_cases/print_variables.json"));
		String lineSeparator = System.lineSeparator(); // uses CRLF / CR depending on system
		assertPrints("Ronal Macdonal :D" + lineSeparator + "7.0" + lineSeparator);
		restoreStreams(); // More magic for console output testing
	}

	private void variableExists(InterpreterState state, String name, Double value) {
		Variable<Double> variable = state.getNumericVariable(name);
		assertNotNull(variable);
		assertEquals(variable.getValue(), value);
	}

	private void variableExists(InterpreterState state, String name, String value) {
		Variable<String> variable = state.getStringVariable(name);
		assertNotNull(variable);
		assertEquals(variable.getValue(), value);
	}

	private void assertPrints(String string) {
		assertEquals(string, outContent.toString());
	}

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	private void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	private void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	private Pair<Interpreter, InterpreterState> interpret(String filePath) throws IOException {
		InterpreterState state = new TestState();
		Interpreter interpreter = new PrintScriptInterpreter(state);
		interpreter.interpret(astBuilder.buildFromJson(filePath));

		return new Pair<>(interpreter, state);
	}
}
