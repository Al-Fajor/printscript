package org.example.ast.statement;

import org.example.Pair;
import org.example.ast.AstComponent;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class AssignationStatement implements SentenceStatement {
    private final AstComponent leftComponent;
    private final AstComponent rightComponent;

    public AssignationStatement(AstComponent leftComponent, AstComponent rightComponent) {
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
    AssignationStatement that = (AssignationStatement) o;
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

  @Override
  public Object getLeft() {
    return null;
  }

  @Override
  public Object getRight() {
    return null;
  }
}
