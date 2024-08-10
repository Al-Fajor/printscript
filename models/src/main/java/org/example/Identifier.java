package org.example;

import java.util.Objects;

public class Identifier implements AstComponent {
    private final String name;
    private final IdentifierType type;

    public Identifier(String name, IdentifierType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public IdentifierType getType() {
        return type;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Identifier that = (Identifier) o;
    return Objects.equals(name, that.name) && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }
}
