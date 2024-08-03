package model;

public class BinaryExpression implements AstComponent {
    private final BinaryOperator operator;
    private final AstComponent leftComponent;
    private final AstComponent rightComponent;

    public BinaryExpression(BinaryOperator operator, AstComponent leftComponent, AstComponent rightComponent) {
        this.operator = operator;
        this.leftComponent = leftComponent;
        this.rightComponent = rightComponent;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public AstComponent getLeftComponent() {
        return leftComponent;
    }

    public AstComponent getRightComponent() {
        return rightComponent;
    }
}
