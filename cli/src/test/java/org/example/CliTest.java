package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;
import org.example.io.ScriptReader;
import org.example.test.TestBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import picocli.CommandLine;

class CliTest extends TestBuilder {
	public static final String TEST_CASE_DIRECTORY = "src/test/resources/test_cases";
	private static final String runOnly = "";

	public CliTest() {
		super(runOnly);
	}

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
			List<String> expectedOutput = CliTestReader.readOutput(path, "expectedOutput");
			List<String> unexpectedOutput = CliTestReader.readOutput(path, "unexpectedOutput");

			if (command.startsWith("format")) {
				createTempCopyAndExecuteCommand(command, cmd);
			} else {
				cmd.execute(command.split(" "));
			}

			String output = outputStream.toString();

			assertExpected(expectedOutput, output);
			assertUnexpected(unexpectedOutput, output);
		};
	}

	private static void assertUnexpected(List<String> expectedOutput, String output) {
		expectedOutput.forEach(
				outputSegment ->
						assertFalse(
								output.contains(outputSegment),
								"Could not find '" + outputSegment + "' in:\n" + output));
	}

	private static void assertExpected(List<String> expectedOutput, String output) {
		expectedOutput.forEach(
				outputSegment ->
						assertTrue(
								output.contains(outputSegment),
								"Could not find '" + outputSegment + "' in:\n" + output));
	}

	private void createTempCopyAndExecuteCommand(String command, CommandLine cmd)
			throws IOException {
		String[] splitCommand = command.split(" ");

		Path pathOfFileToFormat = Paths.get(splitCommand[1]);

		Path tempDir = Files.createTempDirectory(Paths.get("src/test/resources"), "tmpDirPrefix");
		Path tempPath = Files.createTempFile(tempDir, "testfile", ".pts");

		Files.copy(
				pathOfFileToFormat.toAbsolutePath(), tempPath, StandardCopyOption.REPLACE_EXISTING);

		try {
			formatCopy(cmd, splitCommand, tempPath, pathOfFileToFormat);
		} finally {
			Files.delete(tempPath);
			Files.delete(tempDir);
		}
	}

	private void formatCopy(
			CommandLine cmd, String[] splitCommand, Path tempPath, Path pathOfFileToFormat)
			throws FileNotFoundException {
		String command;
		splitCommand[1] = tempPath.toString();
		command = String.join(" ", splitCommand);

		cmd.execute(command.split(" "));

		String actualFormattedCode =
				ScriptReader.readCodeFromSource(tempPath.toString())
						.replace("\n", "")
						.replace("\r", "");
		String expectedFormattedCode =
				ScriptReader.readCodeFromSource(
						pathOfFileToFormat.toString().replace("unformatted", "formatted"));

		assertEquals(normalized(expectedFormattedCode), normalized(actualFormattedCode));
	}

	private String normalized(String code) {
		return code.replace("\n", "").replace("\r", "");
	}
}
