package model;

public class Identifier implements AstComponent {
    private final String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
