package org.example;

import java.io.File;
import java.util.stream.Stream;
import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

public class InterpreterTests extends TestBuilder {
	private final InterpreterTester tester = new InterpreterTester();
	private static final String TEST_CASES = "src/test/resources/test_cases";

	@TestFactory
	protected Stream<DynamicTest> testAllDirectoryCases() {
		return super.testAllDirectoryCases(TEST_CASES);
	}

	@Override
	protected Executable getTestExecutable(File testFile) {
		return () -> tester.test(testFile.getPath());
	}
}
