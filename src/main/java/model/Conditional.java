package model;

public class Conditional implements AstComponent {
    private final AstComponent condition;

    public Conditional(AstComponent condition) {
        this.condition = condition;
    }

    public AstComponent getCondition() {
        return condition;
    }
}
