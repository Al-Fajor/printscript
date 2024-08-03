package model;

public class Assignation implements AstComponent {
    private final AstComponent leftComponent;
    private final AstComponent rightComponent;

    public Assignation(AstComponent leftComponent, AstComponent rightComponent) {
        this.leftComponent = leftComponent;
        this.rightComponent = rightComponent;
    }

    public AstComponent getLeftComponent() {
        return leftComponent;
    }

    public AstComponent getRightComponent() {
        return rightComponent;
    }
}
