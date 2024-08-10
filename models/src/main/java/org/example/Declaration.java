package org.example;

import java.util.Objects;

public class Declaration implements AstComponent {
    private final DeclarationType type;
    private final String name;

    public Declaration(DeclarationType type, String name) {
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
    Declaration that = (Declaration) o;
    return type == that.type && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, name);
  }
}
