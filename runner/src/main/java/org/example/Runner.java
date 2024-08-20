package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.example.ast.*;
import org.example.lexerresult.LexerSuccess;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;

public class Runner {
	public void run(String code) {
		Lexer lexer = new PrintScriptLexer();
		SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();
		SemanticAnalyzer semanticAnalyzer = getSemanticAnalyzer();
		//		Interpreter interpreter = new PrintScriptInterpreter();

		Result lexerResult = lexer.lex(code);

		if (!lexerResult.isSuccessful()) {
			throw new RuntimeException((lexerResult).errorMessage());
		}
		LexerSuccess lexerSuccess = (LexerSuccess) lexerResult;
		SyntaxResult syntaxResult = syntaxAnalyzer.analyze(lexerSuccess.getTokens());

		if (!syntaxResult.isSuccessful()) {
			throw new RuntimeException(syntaxResult.errorMessage());
		}

		List<AstComponent> components = syntaxResult.getComponents();
		Result result = semanticAnalyzer.analyze(components);

		if (!result.isSuccessful()) {
			throw new RuntimeException("Semantic error");

		} else {
			//			interpreter.interpret(components);
		}
	}

	private static SemanticAnalyzer getSemanticAnalyzer() {
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
		return semanticAnalyzer;
	}
}
