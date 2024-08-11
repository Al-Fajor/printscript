package org.example.ast;

import org.example.Pair;
import org.example.ast.visitor.Visitable;

public interface AstComponent extends Visitable {
  Pair<Integer, Integer> getStart();
  Pair<Integer, Integer> getEnd();
}
