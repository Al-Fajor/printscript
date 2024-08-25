package org.example.commands;

import static org.example.utils.PrintUtils.printFailedCode;
import static org.example.utils.ReadUtils.getContent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import org.example.ConfigReader;
import org.example.PrintScriptSCA;
import org.example.Result;
import org.example.io.Color;
import picocli.CommandLine;

@CommandLine.Command(
		name = "analyze",
		description = "Checks for convention or good practice violations in the file")
public class AnalyzeCommand implements Callable<Integer> {
	private static final String DEFAULT_CONFIG_PATH =
			"src/main/java/org/example/config/defaultScaConfig.json";

	@CommandLine.Parameters(index = "0", description = "The file to be analyzed.")
	private String filePath;

	@CommandLine.Option(
			names = "--version",
			description = "The PrintScript version of the code being analyzed")
	private int version;

	@CommandLine.Option(
			names = "--config",
			description = "The path to the analyzer configuration file")
	private String configPath;

	@Override
	public Integer call() {
		String configPathOrDefault = Objects.requireNonNullElse(configPath, DEFAULT_CONFIG_PATH);
		PrintScriptSCA analyzer = getAnalyzer(configPathOrDefault);
		String content = getContent(filePath);

		List<Result> results = analyzer.analyze(content);

		Color.printGreen("Running analyzer...");

		if (results.getFirst().isSuccessful()) {
			Color.printGreen("Static Code Analyzer found no errors.");
		} else {
			Color.printGreen("Found errors:");
			results.forEach(
					result -> {
						System.out.println("- " + result.errorMessage());
						printFailedCode(filePath, result, "Static Code Analysis");
					});
		}

		return 0;
	}

	private PrintScriptSCA getAnalyzer(String configPathOrDefault) {
		PrintScriptSCA analyzer;
		try {
			analyzer = new PrintScriptSCA(new ConfigReader(configPathOrDefault));
		} catch (IOException e) {
			throw new RuntimeException("Could not read config file. Got error:\n" + e);
		}
		return analyzer;
	}
}
