package org.example.ast;

import org.example.Pair;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class Literal<K> implements EvaluableComponent {
    private final K value;

    public Literal(K value) {
        this.value = value;
    }

    public K getValue() {
        return value;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Literal<?> literal = (Literal<?>) o;
    return Objects.equals(value, literal.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
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
  public <T> T accept(Visitor<T> visitor) {
    return visitor.visit(this);
  }
}
