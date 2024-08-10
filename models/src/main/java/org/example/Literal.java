package org.example;

import java.util.Objects;

public class Literal<T> implements AstComponent {
    private final T value;

    public Literal(T value) {
        this.value = value;
    }

    public T getValue() {
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
}
