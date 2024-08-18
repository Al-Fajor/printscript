package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.example.ast.AstComponent;
import org.example.ast.DeclarationType;
import org.example.commands.Color;
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
		String code = ScriptReader.readCodeFromSource(path);

		Color.printGreen("Performing lexical analysis");
		LexerResult lexerResult = lexer.lex(code);
		if (lexingFailed(lexerResult)) return Collections.emptyList();

		Color.printGreen("Performing syntactic analysis");
		SyntaxResult syntaxResult = syntaxAnalyzer.analyze(lexerResult.getTokens());
		if (syntaxAnalysisFailed(syntaxResult)) return Collections.emptyList();

		Color.printGreen("Performing semantic analysis");
		if (semanticAnalysisFailed(syntaxResult)) return Collections.emptyList();

		return syntaxResult.getComponents();
	}

	private boolean semanticAnalysisFailed(SyntaxResult syntaxResult) {
		semanticAnalyzer.addObserver(new SemanticAnalyzerObserver());
		SemanticResult semanticResult = semanticAnalyzer.analyze(syntaxResult.getComponents());

		if (!semanticResult.isSuccessful()) {
			System.out.println(
					"Semantic analysis failed; got error at :"
							+ semanticResult.getErrorStart()
							+ ": "
							+ semanticResult.getErrorEnd());
			semanticResult.errorMessage();
			return true;
		}
		return false;
	}

	private static boolean syntaxAnalysisFailed(SyntaxResult syntaxResult) {
		if (syntaxResult.isFailure()) {
			System.out.println("Syntax analysis failed; got error:");
			// TODO: print error
			return true;
		}
		return false;
	}

	private static boolean lexingFailed(LexerResult lexerResult) {
		if (!lexerResult.isSuccessful()) {
			System.out.println("Lexing failed; got error:");
			// TODO: print error
			return true;
		}
		return false;
	}
}
