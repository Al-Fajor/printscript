package org.example.commands;

import static org.example.utils.PrintUtils.printFailedCode;
import static org.example.utils.ReadUtils.getContent;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.example.Lexer;
import org.example.PrintScriptLexer;
import org.example.PrintScriptSca;
import org.example.Result;
import org.example.io.Color;
import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;
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
		PrintScriptSca analyzer = getAnalyzer(configPathOrDefault);
		String content = getContent(filePath);
        Iterator<String> iterator = content.lines().iterator();
        Lexer lexer = new PrintScriptLexer();
        List<Result> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Result lexerResult = lexer.lex(iterator);
            if (!lexerResult.isSuccessful()) {
                printFailedCode(filePath, lexerResult, "Lexing");
                return 1;
            }
            LexerSuccess lexerSuccess = (LexerSuccess) lexerResult;
            results.addAll(analyzer.analyze(lexerSuccess.getTokens()));
        }

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
