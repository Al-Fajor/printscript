package semantic_analyzer;

import model.Assignation;
import model.AstBuilder;
import model.AstComponent;
import model.BinaryExpression;
import model.Identifier;
import model.Literal;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AnalyzerImplTest {
    public static final String TEST_CASE_DIRECTORY = "C:\\Users\\tomas\\projects\\ingsis\\src\\test\\resources\\semantic_test_cases";
    AstBuilder builder = new AstBuilder();
    AstValidityChecker checker = new AstValidityChecker();
    SemanticAnalyzer semanticAnalyzer = new AnalyzerImpl(
            Map.of(
                    Assignation.class, new AssignationResolver(),
                    BinaryExpression.class, new BinaryExpressionResolver(),
                    Literal.class, new LiteralResolver(),
                    Identifier.class, new IdentifierResolver()
            )
    );

    @TestFactory
    Stream<DynamicTest> generateTests() {
        File directory = new File(TEST_CASE_DIRECTORY);
        return Arrays.stream(directory.listFiles())
                .map((File testFile) ->
                        DynamicTest.dynamicTest(
                                testFile.getName(),
                                getTestExecutable(testFile)
                        )
                );
    }

    private Executable getTestExecutable(File testFile) {
        return () -> {
            try {
                List<AstComponent> astList = builder.buildFromJson(testFile.getAbsolutePath());
                SemanticResult analyticResult = semanticAnalyzer.analyze(astList);
                boolean validity = checker.readValidityFromJson(testFile.getAbsolutePath());

                assertAndPrintError(analyticResult, validity);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't read file to build AST");
            }
        };
    }

    private static void assertAndPrintError(SemanticResult analyticResult, boolean validity) {
        if (analyticResult.isFailure()) {
            System.out.println("Semantic Error: " + ((SemanticFailure) analyticResult).getReason());
        }
        if (validity) assertFalse(analyticResult.isFailure());
        else assertTrue(analyticResult.isFailure());
    }
}