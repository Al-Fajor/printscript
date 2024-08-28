package org.example.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

public abstract class TestBuilder {
	private final String runOnly; /* Modify as pleased whenever you want to debug a single test */

	/**
	 * @param runOnly = Write only the file name, not the full directory
	 */
	public TestBuilder(String runOnly) {
		this.runOnly = runOnly;
	}

	public TestBuilder() {
		this.runOnly = "";
	}

	/** Annotate with @TestFactory whenever you want to create tests from a directory. */
	protected Stream<DynamicTest> testAllDirectoryCases(String testCaseDirectory) {
		Stream<File> files;

		if (runOnly.isEmpty()) {
			Path resourcePath = Paths.get(testCaseDirectory);
			File directory = resourcePath.toFile();

			files = Arrays.stream(Objects.requireNonNull(directory.listFiles()));
		} else {
			Path resourcePath = Paths.get(testCaseDirectory + File.separator + runOnly);
			File singleFile = resourcePath.toFile();

			files = Stream.of(singleFile);
		}

		return files.map(
				(File testFile) ->
						DynamicTest.dynamicTest(testFile.getName(), getTestExecutable(testFile)));
	}

	/** IMPLEMENT. */
	protected abstract Executable getTestExecutable(File testFile);
}
