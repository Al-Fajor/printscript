package org.example;

import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;

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

    SyntaxResult syntaxResult = syntaxAnalyzer.analyze(lexer.lex(code));

    if(syntaxResult.isFailure() && syntaxResult instanceof SyntaxError){
      System.out.println(((SyntaxError) syntaxResult).getReason());
      return;
    }

    List<AstComponent> components = syntaxResult.getComponents();
    SemanticResult result = semanticAnalyzer.analyze(components);

    if(result.isFailure()){
      System.out.println("Semantic error");
    }
    else{
      interpreter.interpret(components);
    }




  }

  private static SemanticAnalyzer getSemanticAnalyzer() {
    SemanticAnalyzer semanticAnalyzer;

    {
      Map<Class<? extends AstComponent>, Resolver> resolverMap = Map.of(
        Assignation.class, new AssignationResolver(),
        BinaryExpression.class, new BinaryExpressionResolver(),
        Literal.class, new LiteralResolver(),
        Identifier.class, new IdentifierResolver(),
        FunctionCall.class, new FunctionCallResolver(),
        Parameters.class, new ParametersResolver()
      );
      MapEnvironment env = new MapEnvironment(
        new HashMap<String, DeclarationType>(),
        Set.of(
          new Signature("println", List.of(DeclarationType.NUMBER)),
          new Signature("println", List.of(DeclarationType.STRING))
        )
      );
      semanticAnalyzer = new AnalyzerImpl(resolverMap, env);
    }
    return semanticAnalyzer;
  }
}
