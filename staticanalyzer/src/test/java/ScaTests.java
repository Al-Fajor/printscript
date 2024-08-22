import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class ScaTests extends TestBuilder {
	ScaTester tester = new ScaTester();
	String TEST_CASES = "staticanalyzer/src/test/resources/test_cases";

	public ScaTests() throws IOException {}

	@TestFactory
	public Stream<DynamicTest> testAllDirectoryCases() {
		return super.testAllDirectoryCases(TEST_CASES);
	}

	@Override
	protected Executable getTestExecutable(File testFile) {
		return () -> tester.test(testFile.getPath());
	}
}
