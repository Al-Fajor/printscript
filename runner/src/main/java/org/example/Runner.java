package org.example;

import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerResult;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;

import javax.naming.spi.Resolver;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Runner {
  public void run(String code){
    Lexer lexer = new PrintScriptLexer();
    SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();
    SemanticAnalyzer semanticAnalyzer = getSemanticAnalyzer();
    Interpreter interpreter = new PrintScriptInterpreter();

    LexerResult lexerResult = lexer.lex(code);

    if (!lexerResult.isSuccessful()) {
      System.out.println(((LexerFailure) lexerResult).message());
      return;
    }

    SyntaxResult syntaxResult = syntaxAnalyzer.analyze(lexerResult.getTokens());

    if(syntaxResult.isFailure() && syntaxResult instanceof SyntaxError){
      System.out.println(((SyntaxError) syntaxResult).getReason());
      return;
    }

    List<AstComponent> components = syntaxResult.getComponents();
    SemanticResult result = semanticAnalyzer.analyze(components);

    if(!result.isSuccessful()){
      System.out.println("Semantic error: " + result.errorMessage());
    }
    else{
      interpreter.interpret(components);
    }
  }

  private static SemanticAnalyzer getSemanticAnalyzer() {
    SemanticAnalyzer semanticAnalyzer;

    {
      MapEnvironment env = new MapEnvironment(
        new HashMap<String, DeclarationType>(),
        Set.of(
          new Signature("println", List.of(DeclarationType.NUMBER)),
          new Signature("println", List.of(DeclarationType.STRING))
        )
      );
      semanticAnalyzer = new SemanticAnalyzerImpl(env);
    }
    return semanticAnalyzer;
  }
}
