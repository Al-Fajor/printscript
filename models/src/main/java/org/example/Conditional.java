package org.example;

import java.util.Objects;

public class Conditional implements AstComponent {
    private final AstComponent condition;

    public Conditional(AstComponent condition) {
        this.condition = condition;
    }

    public AstComponent getCondition() {
        return condition;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Conditional that = (Conditional) o;
    return Objects.equals(condition, that.condition);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(condition);
  }
}
