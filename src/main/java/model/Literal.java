package model;

public class Literal implements AstComponent {
    private final String value;

    public Literal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
