package org.example.ast.statement;

import org.example.Pair;
import org.example.ast.DeclarationType;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class DeclarationStatement implements SentenceStatement {
    private final DeclarationType type;
    private final String name;

    public DeclarationStatement(DeclarationType type, String name) {
        this.type = type;
        this.name = name;
    }

    public DeclarationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeclarationStatement that = (DeclarationStatement) o;
    return type == that.type && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, name);
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
  public<T> T accept(Visitor<T> visitor) {
    return visitor.visit(this);
  }
}
