package org.example.ast;

import org.example.Pair;
import org.example.ast.statement.SentenceStatement;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class IfStatement implements SentenceStatement {
    private final Conditional conditional;
    private final AstComponent trueClause;
    private final AstComponent falseClause;

    public IfStatement(Conditional conditional, AstComponent trueClause, AstComponent falseClause) {
        this.conditional = conditional;
        this.trueClause = trueClause;
        this.falseClause = falseClause;
    }

    public Conditional getConditional() {
        return conditional;
    }

    public AstComponent getFalseClause() {
        return falseClause;
    }

    public AstComponent getTrueClause() {
        return trueClause;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IfStatement that = (IfStatement) o;
    return Objects.equals(conditional, that.conditional) && Objects.equals(trueClause, that.trueClause) && Objects.equals(falseClause, that.falseClause);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conditional, trueClause, falseClause);
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
