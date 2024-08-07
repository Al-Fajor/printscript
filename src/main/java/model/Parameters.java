package model;

import java.util.List;
import java.util.Objects;

public class Parameters implements AstComponent {
    private final List<AstComponent> parameters;

    public Parameters(List<AstComponent> parameters) {
        this.parameters = parameters;
    }

    public List<AstComponent> getParameters() {
        return parameters;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Parameters that = (Parameters) o;
    return Objects.equals(parameters, that.parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(parameters);
  }

  @Override
  public String toString() {
    return "Parameters{" +
      "parameters=" + parameters +
      '}';
  }
}
