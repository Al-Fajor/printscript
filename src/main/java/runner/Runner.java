package runner;

import interpreter.Interpreter;
import interpreter.PrintScriptInterpreter;
import lexer.Lexer;
import lexer.PrintScriptLexer;
import model.*;
import parser.semantic_analyzer.*;
import parser.syntax.analyzer.SyntaxAnalyzer;
import parser.syntax.analyzer.SyntaxAnalyzerImpl;
import parser.syntax.result.SyntaxError;
import parser.syntax.result.SyntaxResult;

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

    if (isSyntaxError(syntaxResult)) return;

    List<AstComponent> components = syntaxResult.getComponents();
    SemanticResult result = semanticAnalyzer.analyze(components);

    if (isSemanticError(result)) return;

    interpreter.interpret(components);

  }

  private boolean isSemanticError(SemanticResult result) {
    if(result.isFailure()){
      System.out.println(((SemanticFailure) result).getReason());
      return true;
    }
    return false;
  }

  //Private
  private boolean isSyntaxError(SyntaxResult syntaxResult) {
    if(syntaxResult.isFailure()){
      System.out.println(((SyntaxError) syntaxResult).getReason());
      return true;
    }
    return false;
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
        new HashMap<>(),
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
