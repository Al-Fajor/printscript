import java.io.File;
import java.util.stream.Stream;
import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

public class ScaTests11 extends TestBuilder {
	ScaTester tester = new ScaTester();
	String version = "1.1";
	String testCases = "src/test/resources/test_cases/1-1";

	public ScaTests11() {
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
