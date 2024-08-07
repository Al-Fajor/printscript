package parser.syntax.result;

import model.AstComponent;

import java.util.List;

public class SyntaxError implements SyntaxResult{
  private final String reason;

  public SyntaxError(String reason) {
    this.reason = reason;
  }

  @Override
  public boolean isFailure() {
    return true;
  }

  public String getReason() {
    return reason;
  }

  @Override
  public List<AstComponent> getComponents() {
    return List.of();
  }
}
