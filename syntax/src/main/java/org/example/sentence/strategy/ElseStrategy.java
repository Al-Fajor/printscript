<<<<<<<< HEAD:src/main/java/parser/syntax/sentence/builder/IfBuilder.java
package parser.syntax.sentence.builder;
========
package org.example.sentence.strategy;
>>>>>>>> 6035302c02e585b433224c294c5bf11a88e57129:syntax/src/main/java/org/example/sentence/strategy/ElseStrategy.java

import org.example.ast.AstComponent;
import org.example.token.Token;

import java.util.List;

public class IfBuilder implements SentenceBuilder {
  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    return null;
  }
}
