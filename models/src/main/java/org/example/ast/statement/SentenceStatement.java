package org.example.ast.statement;

import org.example.ast.AstComponent;

public interface SentenceStatement<K,W> extends AstComponent {
  //TODO: declare types on Assignation, Declaration and FunctionCall Statements
  K getLeft();
  W getRight();
}
