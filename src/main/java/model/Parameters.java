package model;

import java.util.List;

public class Parameters implements AstComponent {
    private final List<AstComponent> parameters;

    public Parameters(List<AstComponent> parameters) {
        this.parameters = parameters;
    }

    public List<AstComponent> getParameters() {
        return parameters;
    }
}
