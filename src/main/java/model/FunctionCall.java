package model;

import java.util.Objects;

public class FunctionCall implements AstComponent {
    private final Identifier identifier;
    private final Parameters parameters;

    public FunctionCall(Identifier identifier, Parameters parameters) {
        this.identifier = identifier;
        this.parameters = parameters;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Parameters getParameters() {
        return parameters;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FunctionCall that = (FunctionCall) o;
    return Objects.equals(identifier, that.identifier) && Objects.equals(parameters, that.parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier, parameters);
  }

  @Override
  public String toString() {
    return "FunctionCall{" +
      "identifier=" + identifier +
      ", parameters=" + parameters +
      '}';
  }
}
