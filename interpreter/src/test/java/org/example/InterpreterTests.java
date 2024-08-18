package org.example;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class InterpreterTests {
	private final InterpreterTester tester = new InterpreterTester();

	@Test
	public void testAssignation() throws IOException {
		tester.test("src/test/resources/test_cases/assign_numeric_variable.json");
		tester.test("src/test/resources/test_cases/assign_string_variable.json");
	}

	@Test
	public void testDeclaration() throws IOException {
		tester.test("src/test/resources/test_cases/declare_numeric_variable.json");
		tester.test("src/test/resources/test_cases/declare_string_variable.json");
	}

	@Test
	public void testNumberOperations() throws IOException {
		tester.test("src/test/resources/test_cases/number_operations.json");
	}

	@Test
	public void testPrint() throws IOException {
		tester.test("src/test/resources/test_cases/print_variables.json");
	}

	@Test
	public void testConcatenation() throws IOException {
		tester.test("src/test/resources/test_cases/string_concatenation.json");
	}
}
