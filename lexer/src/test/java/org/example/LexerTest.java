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
	private static final String runOnly = "";

	public LexerTest() {
		super();
	}

	//    Token Detection
	@TestFactory
	protected Stream<DynamicTest> testValidCases1_0() {
		return super.testAllDirectoryCases(testCaseDirectory + "1-0/valid");
	}

    @TestFactory
    protected Stream<DynamicTest> testValidCases1_1() {
        return super.testAllDirectoryCases(testCaseDirectory + "1-1/valid");
    }

	//    Token Error Detection
	@TestFactory
	protected Stream<DynamicTest> testInvalidCases1_0() {
		return super.testAllDirectoryCases(testCaseDirectory + "1-0/invalid");
	}

    @TestFactory
    protected Stream<DynamicTest> testInvalidCases1_1() {
        return super.testAllDirectoryCases(testCaseDirectory + "1-1/invalid");
    }

	@Override
	protected Executable getTestExecutable(File testFile) {
		String filePath = String.valueOf(testFile);
		boolean isTestingFailure = filePath.contains(File.separator + "invalid" + File.separator);
        String version = extractVersionFromPath(filePath);
        return isTestingFailure
				? () -> lexerTestBuilder.testLexicalErrorDetection(filePath, version)
				: () -> lexerTestBuilder.testTokenDetection(filePath, version);
	}

    private String extractVersionFromPath(String filePath) {
        String[] parts = filePath.split(File.separator);
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("valid") || parts[i].equals("invalid")) {
                return parts[i - 1].replace('-', '.');
            }
        }
        return "";
    }
}
