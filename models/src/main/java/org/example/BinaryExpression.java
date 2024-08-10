package org.example;

import java.util.Objects;

public class BinaryExpression implements AstComponent {
    private final BinaryOperator operator;
    private final AstComponent leftComponent;
    private final AstComponent rightComponent;

    public BinaryExpression(BinaryOperator operator, AstComponent leftComponent, AstComponent rightComponent) {
        this.operator = operator;
        this.leftComponent = leftComponent;
        this.rightComponent = rightComponent;
    }

    public BinaryOperator getOperator() {
        return operator;
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
    BinaryExpression that = (BinaryExpression) o;
    return operator == that.operator && Objects.equals(leftComponent, that.leftComponent) && Objects.equals(rightComponent, that.rightComponent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operator, leftComponent, rightComponent);
  }
}
