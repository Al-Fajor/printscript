package org.example;

import java.util.Objects;

public class Assignation implements AstComponent {
    private final AstComponent leftComponent;
    private final AstComponent rightComponent;

    public Assignation(AstComponent leftComponent, AstComponent rightComponent) {
        this.leftComponent = leftComponent;
        this.rightComponent = rightComponent;
    }

    public AstComponent getLeftComponent() {
        return leftComponent;
    }

    public AstComponent getRightComponent() {
        return rightComponent;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Assignation that = (Assignation) o;
    return Objects.equals(leftComponent, that.leftComponent) && Objects.equals(rightComponent, that.rightComponent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(leftComponent, rightComponent);
  }

  @Override
  public String
  toString() {
    return "Assignation{" +
      "leftComponent=" + leftComponent +
      ", rightComponent=" + rightComponent +
      '}';
  }
}
