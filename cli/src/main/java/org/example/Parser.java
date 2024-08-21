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
import org.example.lexerresult.LexerSuccess;
import org.example.result.SyntaxError;
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
        Result lexerResult = lexer.lex(code);
        if (lexingFailed(lexerResult)) return Collections.emptyList();

        Color.printGreen("\nPerforming syntactic analysis");
        SyntaxResult syntaxResult =
                syntaxAnalyzer.analyze(((LexerSuccess) lexerResult).getTokens());
        if (syntaxAnalysisFailed(syntaxResult)) return Collections.emptyList();

        Color.printGreen("\nPerforming semantic analysis");
        if (semanticAnalysisFailed(syntaxResult, path)) return Collections.emptyList();

        return syntaxResult.getComponents();
    }

    private boolean semanticAnalysisFailed(SyntaxResult syntaxResult, String path) {
        semanticAnalyzer.addObserver(new SemanticAnalyzerBrokerObserver());
        Result semanticResult = semanticAnalyzer.analyze(syntaxResult.getComponents());

        if (!semanticResult.isSuccessful()) {
            String coloredSegment;
            try {
                coloredSegment = ScriptReader.readAndHighlightRange(
                        path, semanticResult.getErrorStart().get(),
                        semanticResult.getErrorEnd().get());
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
        if (!syntaxResult.isSuccessful()) {
            SyntaxError error = (SyntaxError) syntaxResult;

            System.out.println("Syntax analysis failed with error: \n " + error.errorMessage());
            System.out.println(
                    "From line: "
                            + error.getErrorStart().get().first()
                            + ", column: "
                            + error.getErrorStart().get().second());
            System.out.println(
                    "To line: "
                            + error.getErrorEnd().get().first()
                            + ", column: "
                            + error.getErrorEnd().get().second());

            return true;
        }
        return false;
    }

    private static boolean lexingFailed(Result lexerResult) {
        if (!lexerResult.isSuccessful()) {
            System.out.println("Lexing failed with error");
            System.out.println(lexerResult.errorMessage());
            return true;
        }
        return false;
    }
}
