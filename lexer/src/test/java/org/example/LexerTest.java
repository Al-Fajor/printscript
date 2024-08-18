package org.example;

import java.io.File;
import java.util.stream.Stream;
import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

class LexerTest extends TestBuilder {
	LexerTestBuilder lexerTestBuilder = new LexerTestBuilder();
	private static final String testCaseDirectory = "src/test/resources/test_cases/";

	//    Token Detection
	@TestFactory
	protected Stream<DynamicTest> testValidCases() {
		return super.testAllDirectoryCases(testCaseDirectory + "valid");
	}

	//    Token Error Detection
	@TestFactory
	protected Stream<DynamicTest> testInvalidCases() {
		return super.testAllDirectoryCases(testCaseDirectory + "invalid");
	}

	@Override
	protected Executable getTestExecutable(File testFile) {
		String filePath = String.valueOf(testFile);
		boolean testsFailure = filePath.contains("/invalid/");
		return testsFailure
				? () -> lexerTestBuilder.testLexicalErrorDetection(filePath)
				: () -> lexerTestBuilder.testTokenDetection(filePath);
	}
}
