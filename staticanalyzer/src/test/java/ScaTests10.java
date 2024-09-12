import java.io.File;
import java.util.stream.Stream;
import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

public class ScaTests10 extends TestBuilder {
	ScaTester tester = new ScaTester();
	String version = "1.0";
	String testCases = "src/test/resources/test_cases/1-0";

	public ScaTests10() {
		super();
	}

	@TestFactory
	public Stream<DynamicTest> testAllDirectoryCases() {
		return super.testAllDirectoryCases(testCases);
	}

	@Override
	protected Executable getTestExecutable(File testFile) {
		return () -> tester.test(testFile.getPath(), version);
	}
}
