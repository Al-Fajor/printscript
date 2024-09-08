package interpreter;

import org.example.Result;
import org.example.SyntaxAnalyzer;
import org.example.SyntaxAnalyzerImpl;
import org.example.ast.statement.Statement;
import org.example.io.Color;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;
import org.example.result.SyntaxSuccess;
import org.xml.sax.ErrorHandler;

import java.util.Iterator;

import static org.example.utils.PrintUtils.printFailedCode;

public class SemanticAnalyzerIterator implements Iterator<Statement> {
    private final interpreter.SyntaxAnalyzerIterator syntaxIterator;
    private final SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();
    private final String path;
    private Statement next;

    public SemanticAnalyzerIterator(interpreter.SyntaxAnalyzerIterator syntaxIterator, String path) {
        this.syntaxIterator = syntaxIterator;
        this.path = path;
    }

    @Override
    public boolean hasNext() {
        if (!syntaxIterator.hasNext()) return false;
        else return loadNextAndEvaluateResult();
    }

    private boolean loadNextAndEvaluateResult() {
        Color.printGreen("\nPerforming syntactic analysis");
        SyntaxResult result = syntaxAnalyzer.analyze(syntaxIterator);

        return switch (result) {
            case SyntaxError failure -> {
                stepFailed(path, result, "Syntax Analysis");
                yield false;
            }
            case SyntaxSuccess success -> {
                next = success.getStatement();
                yield true;
            }
            default -> throw new IllegalStateException("Unexpected result for syntax analyzer: " + result);
        };
    }

    @Override
    public Statement next() {
//        isBufferedForInterpreter = !isBufferedForInterpreter;

        return next;
    }

    private static void stepFailed(String path, Result result, String stepName) {
        if (!result.isSuccessful()) {
            System.out.println(stepName + " failed with error: '" + result.errorMessage() + "'");
            printFailedCode(path, result, stepName);
        }
    }
}