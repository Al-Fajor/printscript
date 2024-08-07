package runner;

import interpreter.Interpreter;
import interpreter.PrintScriptInterpreter;
import lexer.Lexer;
import lexer.PrintScriptLexer;
import model.*;
import parser.syntax.SyntaxAnalyzer;
import parser.syntax.SyntaxAnalyzerImpl;
import semantic_analyzer.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Runner {
  public void run(String code){
    Lexer lexer = new PrintScriptLexer();
    SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();
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
    Interpreter interpreter = new PrintScriptInterpreter();

    List<AstComponent> components = syntaxAnalyzer.analyze(lexer.lex(code));
    SemanticResult result = semanticAnalyzer.analyze(components);
    if(result.isFailure()){
      System.out.println("Failure");
    }
    else{
      interpreter.interpret(components);
    }




  }
}
