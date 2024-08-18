package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.example.ast.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

class SemanticAnalyzerImplTest {
	//    private static final String RUN_ONLY = "assign_and_print_number.json";
	private static final String RUN_ONLY = "";

	public static final String TEST_CASE_DIRECTORY = "src/test/resources/test_cases";
	AstBuilder builder = new AstBuilder();
	AstValidityChecker checker = new AstValidityChecker();
	SemanticAnalyzer semanticAnalyzer;

	{
		MapEnvironment env =
				new MapEnvironment(
						new HashMap<>(),
						Set.of(
								new Signature("println", List.of(DeclarationType.NUMBER)),
								new Signature("println", List.of(DeclarationType.STRING))));
		semanticAnalyzer = new SemanticAnalyzerImpl(env);
	}

	@TestFactory
	Stream<DynamicTest> semanticTests() {
		Stream<File> files;

		if (RUN_ONLY.isEmpty()) {
			//            File directory = new File(TEST_CASE_DIRECTORY);

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

	private Executable getTestExecutable(File testFile) {
		return () -> {
			try {
				List<AstComponent> astList = builder.buildFromJson(testFile.getAbsolutePath());
				Result analyticResult = semanticAnalyzer.analyze(astList);
				boolean validity = checker.readValidityFromJson(testFile.getAbsolutePath());

				assertAndPrintError(analyticResult, validity);
			} catch (IOException e) {
				throw new RuntimeException("Couldn't read file to build AST");
			}
		};
	}

	private static void assertAndPrintError(Result analyticResult, boolean validity) {
		if (!analyticResult.isSuccessful()) {
			System.out.println("Semantic Error: " + analyticResult.errorMessage());
		}
		if (validity) assertTrue(analyticResult.isSuccessful());
		else assertFalse(analyticResult.isSuccessful());
	}
}
