package org.example;

import static org.example.utils.PrintUtils.printFailedCode;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import org.example.ast.DeclarationType;
import org.example.ast.statement.Statement;
import org.example.io.Color;
import org.example.io.ScriptReader;
import org.example.lexerresult.LexerSuccess;
import org.example.result.SyntaxSuccess;
import org.example.token.Token;

public class Parser {
	ProgressBarObserver observer = new ProgressBarObserver();

	Lexer lexer = new PrintScriptLexer();
	SyntaxAnalyzer syntaxAnalyzer;

	{
		syntaxAnalyzer = new SyntaxAnalyzerImpl();
		syntaxAnalyzer.addObserver(observer);
	}

	SemanticAnalyzer semanticAnalyzer;

	{
		MapEnvironment env =
				new MapEnvironment(
						new HashMap<>(),
						Set.of(
								new Signature("println", List.of(DeclarationType.NUMBER)),
								new Signature("println", List.of(DeclarationType.STRING))));
		semanticAnalyzer = new SemanticAnalyzerImpl(env);
		semanticAnalyzer.addObserver(observer);
	}

	public List<Statement> parse(String path) {
		String code;
		try {
			code = ScriptReader.readCodeFromSource(path);
		} catch (FileNotFoundException e) {
			System.out.println("Could not read file; got error: \n" + e);
			return Collections.emptyList();
		}

		Color.printGreen("\nPerforming lexical analysis");
		Result lexerResult = lexer.lex(code);
		if (stepFailed(path, lexerResult, "Lexing")) return Collections.emptyList();

		Color.printGreen("\nPerforming syntactic analysis");
		Iterator<Token> tokens = ((LexerSuccess) lexerResult).getTokens().iterator();
		List<Result> syntaxResults = getSyntaxResults(tokens);
		if (anyFailure(syntaxResults, path, "Syntax analysis")) return Collections.emptyList();

		List<Statement> components =
				syntaxResults.stream()
						.map(result -> ((SyntaxSuccess) result).getStatement())
						.collect(Collectors.toList());

		Color.printGreen("\nPerforming semantic analysis");
		Iterator<Statement> syntaxOutputIterator = components.iterator();
		while (syntaxOutputIterator.hasNext()) {
			Result semanticResult = semanticAnalyzer.analyze(syntaxOutputIterator);
			if (stepFailed(path, semanticResult, "Semantic Analysis"))
				return Collections.emptyList();
		}

		return components;
	}

	private boolean anyFailure(List<Result> allResults, String path, String stepName) {
		return allResults.stream()
				.anyMatch(currentResult -> stepFailed(path, currentResult, stepName));
	}

	private List<Result> getSyntaxResults(Iterator<Token> tokens) {
		List<Result> results = new ArrayList<>();
		while (tokens.hasNext()) {
			results.add(syntaxAnalyzer.analyze(tokens));
		}
		return results;
	}

	private static boolean stepFailed(String path, Result result, String stepName) {
		if (!result.isSuccessful()) {
			System.out.println(stepName + " failed with error: '" + result.errorMessage() + "'");
			printFailedCode(path, result, stepName);
			return true;
		}
		return false;
	}
}
