package model;

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
}
