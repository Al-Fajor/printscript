package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

public class SyntaxTest {
	String RUN_ONLY = "";
	SyntaxTestBuilder testBuilder = new SyntaxTestBuilder();
	String TEST_CASE_DIRECTORY = "src/test/resources/test_cases";

	@TestFactory
	Stream<DynamicTest> syntaxTests() {
		Stream<File> files;

		if (RUN_ONLY.isEmpty()) {
			Path resourcePath = Paths.get(TEST_CASE_DIRECTORY);
			File directory = resourcePath.toFile();

			files = Arrays.stream(Objects.requireNonNull(directory.listFiles()));
		} else {
			Path resourcePath = Paths.get(TEST_CASE_DIRECTORY + File.separator + RUN_ONLY);
			File singleFile = resourcePath.toFile();

			files = Stream.of(singleFile);
		}

		return files.map(
				(File testFile) ->
						DynamicTest.dynamicTest(testFile.getName(), getTestExecutable(testFile)));
	}

	@Test
	public void debug() throws IOException {
		test("declare_sum.json");
	}

	private void test(String filePath) throws IOException {
		assertTrue(testBuilder.testSyntax(TEST_CASE_DIRECTORY + "/" + filePath));
	}

	private Executable getTestExecutable(File testFile) {
		return () -> {
			try {
				assertTrue(testBuilder.testSyntax(testFile.getPath()));
			} catch (IOException e) {
				throw new RuntimeException("Error getting test file");
			}
		};
	}
}
