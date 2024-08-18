package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.example.ast.AstComponent;
import org.example.ast.DeclarationType;
import org.example.io.Color;
import org.example.io.ScriptReader;
import org.example.lexerresult.LexerResult;
import org.example.result.SyntaxResult;

public class Parser {
	Lexer lexer = new PrintScriptLexer();
	SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();
	SemanticAnalyzer semanticAnalyzer;

	{
		MapEnvironment env =
				new MapEnvironment(
						new HashMap<>(),
						Set.of(
								new Signature("println", List.of(DeclarationType.NUMBER)),
								new Signature("println", List.of(DeclarationType.STRING))));
		semanticAnalyzer = new SemanticAnalyzerImpl(env);
	}

	public List<AstComponent> parse(String path) {
		String code;
		try {
			code = ScriptReader.readCodeFromSource(path);
		} catch (FileNotFoundException e) {
			System.out.println("Could not read file; got error: \n" + e.getMessage());
			return Collections.emptyList();
		}

		Color.printGreen("\nPerforming lexical analysis");
		LexerResult lexerResult = lexer.lex(code);
		if (lexingFailed(lexerResult)) return Collections.emptyList();

		Color.printGreen("\nPerforming syntactic analysis");
		SyntaxResult syntaxResult = syntaxAnalyzer.analyze(lexerResult.getTokens());
		if (syntaxAnalysisFailed(syntaxResult)) return Collections.emptyList();

		Color.printGreen("\nPerforming semantic analysis");
		if (semanticAnalysisFailed(syntaxResult, path)) return Collections.emptyList();

		return syntaxResult.getComponents();
	}

	private boolean semanticAnalysisFailed(SyntaxResult syntaxResult, String path) {
		semanticAnalyzer.addObserver(new SemanticAnalyzerObserver());
		Result semanticResult = semanticAnalyzer.analyze(syntaxResult.getComponents());

		if (!semanticResult.isSuccessful()) {
			String coloredSegment;
			try {
				//                coloredSegment = ScriptReader.readAndHighlightRange(
				//                        path, semanticResult.getErrorStart().get(),
				// semanticResult.getErrorEnd().get());
				// TODO: undo hardcoding once error location is returned within Result
				coloredSegment =
						ScriptReader.readAndHighlightRange(
								path, new Pair<>(1, 3), new Pair<>(2, 6));
			} catch (IOException e) {
				throw new RuntimeException("Could not read file at " + path);
			}
			System.out.println(
					"Semantic analysis failed with error: '"
							+ semanticResult.errorMessage()
							+ "'\n from line "
							+ semanticResult.getErrorStart().get().first()
							+ ", column "
							+ semanticResult.getErrorStart().get().second()
							+ "\n to line "
							+ semanticResult.getErrorEnd().get().first()
							+ ", column "
							+ semanticResult.getErrorEnd().get().second()
							+ "\n\n"
							+ coloredSegment
							+ "\n");

			semanticResult.errorMessage();
			return true;
		}
		return false;
	}

	private static boolean syntaxAnalysisFailed(SyntaxResult syntaxResult) {
		if (syntaxResult.isFailure()) {
			System.out.println("Syntax analysis failed with error");
			// TODO: print error
			System.out.println("[Error cause here]");
			return true;
		}
		return false;
	}

	private static boolean lexingFailed(LexerResult lexerResult) {
		if (!lexerResult.isSuccessful()) {
			System.out.println("Lexing failed with error");
			// TODO: print error
			System.out.println("[Error cause here]");
			return true;
		}
		return false;
	}
}