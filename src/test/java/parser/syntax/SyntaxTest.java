package parser.syntax;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntaxTest {
  String RUN_ONLY = "";
  TestBuilder testBuilder = new TestBuilder();
  String TEST_CASE_DIRECTORY = "src/test/resources/parser/syntax";

  @TestFactory
  Stream<DynamicTest> syntaxTests() {
    Stream<File> files;

    if (RUN_ONLY.isEmpty()) {
      File directory = new File(TEST_CASE_DIRECTORY);
      files = Arrays.stream(Objects.requireNonNull(directory.listFiles()));
    }

    else files = Stream.of(new File(TEST_CASE_DIRECTORY + File.separator + RUN_ONLY));

    return files.map((File testFile) ->
      DynamicTest.dynamicTest(
        testFile.getName(),
        getTestExecutable(testFile)
      )
    );
  }

  private Executable getTestExecutable(File testFile) {
    return ()->{
      try{
        assertTrue(testBuilder.testSyntax(testFile.getPath()));
      }
      catch (IOException e) {
        throw new RuntimeException("Error getting test file");
      }
    };
  }

}
