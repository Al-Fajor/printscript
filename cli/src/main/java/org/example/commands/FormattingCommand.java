package org.example.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import org.example.Formatter;
import org.example.PrintScriptFormatter;
import org.example.ast.AstComponent;
import org.example.ast.statement.Statement;
import org.example.factories.RuleFactoryWithCustomPath;
import org.example.io.Color;
import org.example.io.ScriptReader;
import org.example.iterators.InterpreterIterator;
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

	@Override
	public Integer call() {
		String configPathOrDefault = getAbsolutePath();
		System.out.println(configPathOrDefault);

		Formatter formatter = getFormatter(configPathOrDefault);

		//        TODO change this later
		Scanner scanner;
		scanner = ScriptReader.readCodeFromSourceByLine(filePath);
		//        TODO change this
		Iterator<Statement> statements = new InterpreterIterator(scanner, filePath, "1.0");

		String formattedCode = runFormatter(formatter, statements);

		overwriteOriginalFile(formattedCode);

		return 0;
	}

	private List<AstComponent> toAstList(Iterator<Statement> statementIterator) {
		List<AstComponent> statementList = new ArrayList<>();
		while (statementIterator.hasNext()) {
			statementList.add(statementIterator.next());
		}
		return statementList;
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

	private String runFormatter(Formatter formatter, Iterator<Statement> statements) {
		Color.printGreen("\nRunning formatter...");
		String formattedCode = formatter.format(toAstList(statements));
		System.out.println(formattedCode);
		return formattedCode;
	}
}
