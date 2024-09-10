package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;
import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

public class SyntaxTest extends TestBuilder {
	SyntaxTestProvider testProvider = new SyntaxTestProvider();
	private static final String TEST_CASES_1_0 = "src/test/resources/test_cases/1.0";
	private static final String TEST_CASES_1_1 = "src/test/resources/test_cases/1.1";
	private static final String runOnly = "";

	public SyntaxTest() {
		super();
	}

	@TestFactory
	protected Stream<DynamicTest> testFirstVersionCases() {
		return super.testAllDirectoryCases(TEST_CASES_1_0);
	}

	@TestFactory
	protected Stream<DynamicTest> testOneDotOneVersionCases() {
		return super.testAllDirectoryCases(TEST_CASES_1_1);
	}

	@Test
	public void debug() throws IOException {
		test(TEST_CASES_1_1 + "/" + "nesting_one_if.json");
	}

	@Override
	protected Executable getTestExecutable(File testFile) {
		return () -> {
			try {
				test(testFile.getPath());
			} catch (IOException e) {
				throw new RuntimeException("Error getting test file");
			}
		};
	}

	private void test(String filePath) throws IOException {
		assertTrue(testProvider.testSyntax(filePath));
	}
}
