package parser.syntax;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntaxTest {

  TestBuilder testBuilder = new TestBuilder();

  @Test
  public void emptyShouldPass() throws IOException {
    assertTrue(testBuilder.testSyntax("src/test/resources/parser/syntax/empty_should_do_nothing.json", ExpectedResult.SUCCESS));
  }

  @Test
  public void validNumberDeclaration() throws IOException {
    assertTrue(testBuilder.testSyntax("src/test/resources/parser/syntax/number_declaration.json", ExpectedResult.SUCCESS));
  }

  @Test
  public void validStringDeclaration() throws IOException {
    assertTrue(testBuilder.testSyntax("src/test/resources/parser/syntax/string_declaration.json", ExpectedResult.SUCCESS));
  }

}
