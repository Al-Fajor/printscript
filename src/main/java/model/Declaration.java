package model;

public class Declaration implements AstComponent {
    private final DeclarationType type;
    private final String name;

    public Declaration(DeclarationType type, String name) {
        this.type = type;
        this.name = name;
    }

    public DeclarationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
