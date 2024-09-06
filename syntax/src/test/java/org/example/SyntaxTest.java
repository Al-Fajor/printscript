package org.example;

import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntaxTest extends TestBuilder {
	SyntaxTestProvider testProvider = new SyntaxTestProvider();
	private static final String TEST_CASES = "src/test/resources/test_cases";
	private static final String runOnly = "";

	public SyntaxTest() {
		super();
	}

	@TestFactory
	protected Stream<DynamicTest> testAllDirectoryCases() {
		return super.testAllDirectoryCases(TEST_CASES);
	}

	@Test
	public void debug() throws IOException {
		test("number_reassignation.json");
	}

	@Override
	protected Executable getTestExecutable(File testFile) {
		return () -> {
			try {
				assertTrue(testProvider.testSyntax(testFile.getPath()));
			} catch (IOException e) {
				throw new RuntimeException("Error getting test file");
			}
		};
	}

	private void test(String filePath) throws IOException {
		assertTrue(testProvider.testSyntax(TEST_CASES + "/" + filePath));
	}
}
