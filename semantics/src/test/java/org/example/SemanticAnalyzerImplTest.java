package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.example.ast.*;
import org.example.io.AstBuilder;
import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

class SemanticAnalyzerImplTest extends TestBuilder {
  private static final String runOnly = "";
	public static final String TEST_CASE_DIRECTORY = "src/test/resources/test_cases";
	AstBuilder builder = new AstBuilder();
	AstValidityChecker checker = new AstValidityChecker();
	MapEnvironment env =
			new MapEnvironment(
					new HashMap<>(),
					Set.of(
							new Signature("println", List.of(DeclarationType.NUMBER)),
							new Signature("println", List.of(DeclarationType.STRING))));

	SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzerImpl(env);

  public SemanticAnalyzerImplTest(){
    super(runOnly);
  }

	@TestFactory
	protected Stream<DynamicTest> testAllDirectoryCases() {
		return super.testAllDirectoryCases(TEST_CASE_DIRECTORY);
	}

	@Override
	protected Executable getTestExecutable(File testFile) {
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
