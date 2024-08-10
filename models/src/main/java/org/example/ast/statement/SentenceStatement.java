package org.example.ast.statement;

import org.example.ast.AstComponent;

public interface SentenceStatement extends AstComponent {
  AstComponent getLeft();
  AstComponent getRight();
}
