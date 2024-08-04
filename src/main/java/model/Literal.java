package model;

public class Literal<T> implements AstComponent {
    private final T value;

    public Literal(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
