package model;

public class Identifier implements AstComponent {
    private final String name;
    private final IdentifierType type;

    public Identifier(String name, IdentifierType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public IdentifierType getType() {
        return type;
    }
}
