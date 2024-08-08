package parser.syntax.result;

import model.AstComponent;

import java.util.List;

public interface SyntaxResult {
  boolean isFailure();
  List<AstComponent> getComponents();
}
