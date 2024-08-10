package org.example.ast.statement;

import org.example.Pair;
import org.example.ast.AstComponent;
import org.example.ast.Identifier;
import org.example.ast.Parameters;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class FunctionCallStatement implements SentenceStatement {
    private final Identifier identifier;
    private final Parameters parameters;

    public FunctionCallStatement(Identifier identifier, Parameters parameters) {
        this.identifier = identifier;
        this.parameters = parameters;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Parameters getParameters() {
        return parameters;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FunctionCallStatement that = (FunctionCallStatement) o;
    return Objects.equals(identifier, that.identifier) && Objects.equals(parameters, that.parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier, parameters);
  }

  @Override
  public String toString() {
    return "FunctionCall{" +
      "identifier=" + identifier +
      ", parameters=" + parameters +
      '}';
  }

  @Override
  public Pair<Integer, Integer> getStart() {
    return null;
  }

  @Override
  public Pair<Integer, Integer> getEnd() {
    return null;
  }

  @Override
  public Object getLeft() {
    return null;
  }

  @Override
  public Object getRight() {
    return null;
  }

  @Override
  public <T> T accept(Visitor<T> visitor) {
    return visitor.visit(this);
  }
}
