package org.example;

import static org.example.utils.PrintUtils.printFailedCode;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.example.ast.AstComponent;
import org.example.ast.DeclarationType;
import org.example.io.Color;
import org.example.io.ScriptReader;
import org.example.lexerresult.LexerSuccess;
import org.example.result.SyntaxResult;

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

	public List<AstComponent> parse(String path) {
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
		SyntaxResult syntaxResult =
				syntaxAnalyzer.analyze(((LexerSuccess) lexerResult).getTokens());
		if (stepFailed(path, syntaxResult, "Syntax analysis")) return Collections.emptyList();

		Color.printGreen("\nPerforming semantic analysis");
		Result semanticResult = semanticAnalyzer.analyze(syntaxResult.getComponents());
		if (stepFailed(path, semanticResult, "Semantic Analysis")) return Collections.emptyList();

		return syntaxResult.getComponents();
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
