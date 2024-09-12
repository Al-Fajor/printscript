package org.example.commands;

import static org.example.utils.PrintUtils.printFailedCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.example.Lexer;
import org.example.PrintScriptLexer;
import org.example.PrintScriptSca;
import org.example.Result;
import org.example.io.Color;
import org.example.io.ScriptReader;
import org.example.lexerresult.LexerSuccess;
import picocli.CommandLine;

@CommandLine.Command(
		name = "analyze",
		description = "Checks for convention or good practice violations in the file")
public class AnalyzeCommand implements Callable<Integer> {
	private static final String DEFAULT_CONFIG_PATH =
			"src/main/java/org/example/config/defaultScaConfig.json";

	@CommandLine.Parameters(index = "0", description = "The path of the file to be analyzed.")
	private String filePath;

	@CommandLine.Option(
			names = "--version",
			description = "The PrintScript version of the code being analyzed",
			defaultValue = "1.1")
	private String version;

	@CommandLine.Option(
			names = "--config",
			description = "The path to the analyzer's configuration file",
			defaultValue = DEFAULT_CONFIG_PATH)
	private String configPath;

	private boolean foundErrors = false;

	@Override
	public Integer call() {
		PrintScriptSca analyzer = getAnalyzer(configPath);
		Iterator<String> iterator = ScriptReader.readCodeFromSourceByLine(filePath);
		Lexer lexer = new PrintScriptLexer(version);

		while (iterator.hasNext()) {
			Result lexerResult = lexer.lex(iterator);
			if (!lexerResult.isSuccessful()) {
				if (!foundErrors) {
					foundErrors = true;
					Color.printGreen("Found errors:");
				}

				printFailedCode(filePath, lexerResult);
				return 1;

			} else {
				LexerSuccess lexerSuccess = (LexerSuccess) lexerResult;
				List<Result> analyzerResults = analyzer.analyze(lexerSuccess.getTokens());
				printStaticCodeFailures(analyzerResults);
			}
		}

		if (!foundErrors) {
			Color.printGreen("Static Code Analyzer found no errors.");
		}

		return 0;
	}

	private void printStaticCodeFailures(List<Result> analyzerResults) {
		if (!analyzerResults.getFirst().isSuccessful()) {
			analyzerResults.forEach(
					result -> {
						System.out.println("- " + result.errorMessage());
						printFailedCode(filePath, result);
					});
		}
	}

	private PrintScriptSca getAnalyzer(String configPathOrDefault) {
		PrintScriptSca analyzer;
		try {
			analyzer = new PrintScriptSca(new FileInputStream(configPathOrDefault));
		} catch (IOException e) {
			throw new RuntimeException("Could not read config file. Got error:\n" + e);
		}
		return analyzer;
	}
}
