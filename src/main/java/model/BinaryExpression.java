package model;

import java.util.List;

public class BinaryExpression implements AstComponent, AstComposite {
    private final List<AstComponent> children;

    public BinaryExpression(List<AstComponent> children) {
        this.children = children;
    }

    @Override
    public List<AstComponent> getChildren() {
        return children;
    }
}
