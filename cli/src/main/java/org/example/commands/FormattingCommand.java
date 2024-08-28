package org.example.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import org.example.Formatter;
import org.example.Parser;
import org.example.PrintScriptFormatter;
import org.example.ast.AstComponent;
import org.example.factories.RuleFactoryWithCustomPath;
import org.example.io.Color;
import picocli.CommandLine;

@CommandLine.Command(
		name = "format",
		description = "Modifies the file to make code cleaner, without changing functionality")
public class FormattingCommand implements Callable<Integer> {

	private static final String DEFAULT_CONFIG_PATH =
			"src/main/java/org/example/config/defaultFormatterConfig.json";

	@CommandLine.Parameters(index = "0", description = "The file to be formatted.")
	private String filePath;

	@CommandLine.Option(
			names = "--version",
			description = "The PrintScript version of the code being validate")
	private int version;

	@CommandLine.Option(
			names = "--config",
			description = "The path to the formatting configuration file")
	private String configPath;

	private final Parser parser = new Parser();

	@Override
	public Integer call() {
		String configPathOrDefault = getAbsolutePath();
		System.out.println(configPathOrDefault);

		Formatter formatter = getFormatter(configPathOrDefault);

		List<AstComponent> validatedComponents = parser.parse(filePath);
		if (validatedComponents.isEmpty()) {
			return 1;
		}

		Color.printGreen("\nRunning formatter...");
		String formattedCode = formatter.format(validatedComponents);
		System.out.println(formattedCode);

		overwriteOriginalFile(formattedCode);

		return 0;
	}

	private void overwriteOriginalFile(String formattedCode) {
		try {
			Files.write(Paths.get(filePath), formattedCode.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Could not write formatted code. Got error:\n" + e);
		}
	}

	private String getAbsolutePath() {
		String pathOrDefault = Objects.requireNonNullElse(configPath, DEFAULT_CONFIG_PATH);
		return Paths.get(pathOrDefault).toAbsolutePath().toString();
	}

	private Formatter getFormatter(String configPathOrDefault) {
		var ruleFactoryWithCustomPath = new RuleFactoryWithCustomPath(configPathOrDefault);
		return new PrintScriptFormatter(ruleFactoryWithCustomPath);
	}
}
