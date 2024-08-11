<<<<<<<< HEAD:src/main/java/parser/syntax/sentence/builder/LetBuilder.java
package parser.syntax.sentence.builder;
========
package org.example.sentence.strategy;
>>>>>>>> 6035302c02e585b433224c294c5bf11a88e57129:syntax/src/main/java/org/example/sentence/strategy/LetStrategy.java

import org.example.ast.statement.AssignationStatement;
import org.example.ast.AstComponent;
import org.example.ast.statement.DeclarationStatement;
import org.example.ast.DeclarationType;
import org.example.token.Token;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.LetSentenceValidator;
import org.example.sentence.validator.SentenceValidator;

import java.util.List;
import java.util.Map;

import static org.example.token.BaseTokenTypes.LET;

public class LetBuilder implements SentenceBuilder {

  //LET -> IDENTIFIER("anything") -> COLON-> TYPE("anyType") -> ASSIGNATION -> ANYTHING -> SEMICOLON
  //AST (INORDER) SHOULD BE:
  // Number, Identifier(a), Declaration, Assignation, ANYTHING
  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    final SentenceValidator validator = new LetSentenceValidator();
    if(tokens.getFirst().getType() != LET || !validator.isValidSentence(tokens)) return null;
    return getFinalSentence(tokens);
  }

  private AstComponent getFinalSentence(List<Token> tokens) {
    TokenMapper mapper = new TokenMapper();
    //May need to change method
    Token type = tokens.get(3), identifier = tokens.get(1);

<<<<<<<< HEAD:src/main/java/parser/syntax/sentence/builder/LetBuilder.java
    DeclarationType declarationType = getDeclarationType(type.getValue());

    AstComponent declaration = new Declaration(declarationType, identifier.getValue());
    return new Assignation(declaration, mapper.buildArgument(tokens.subList(5, tokens.size())).getFirst());
========
//    System.out.println("Identifier: " + identifier.getValue());
    AstComponent declaration = new DeclarationStatement(declarationType, mapper.clearInvCommas(identifier.getValue()));
    return new AssignationStatement(declaration, mapper.buildFunctionArgument(tokens.subList(5, tokens.size())).getFirst());
>>>>>>>> 6035302c02e585b433224c294c5bf11a88e57129:syntax/src/main/java/org/example/sentence/strategy/LetStrategy.java
  }

    private DeclarationType getDeclarationType(String type) {
        Map<String, DeclarationType> declarationTypeMap = Map.of(
                "number", DeclarationType.NUMBER,
                "string", DeclarationType.STRING,
                "function", DeclarationType.FUNCTION
        );
        return declarationTypeMap.get(type.toLowerCase());
    }

}
