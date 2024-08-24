package org.example;

import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CliTest extends TestBuilder {
    public static final String TEST_CASE_DIRECTORY = "src/test/resources/test_cases";

	@TestFactory
	protected Stream<DynamicTest> testAllDirectoryCases() {
		return super.testAllDirectoryCases(TEST_CASE_DIRECTORY);
	}

	@Override
	protected Executable getTestExecutable(File testFile) {
		return () -> {
                Cli cli = new Cli();
                CommandLine cmd = new CommandLine(cli);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outputStream));

                String path = testFile.getAbsolutePath();
                String command = CliTestReader.readCommand(path);
                List<String> expectedOutput = CliTestReader.readOutput(path);

                cmd.execute(command.split(" "));

                String output = outputStream.toString();

                expectedOutput.forEach(outputSegment ->
                        assertTrue(output.contains(outputSegment), "Could not find '" + outputSegment + "' in:\n" + output)
                );
		};
	}
}
