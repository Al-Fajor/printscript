package org.example.result;

import org.example.AstComponent;

import java.util.List;

public interface SyntaxResult {
  boolean isFailure();
  List<AstComponent> getComponents();
}
