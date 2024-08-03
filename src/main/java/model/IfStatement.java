package model;

import java.util.List;

public class IfStatement implements AstComponent, AstComposite {
    private final List<AstComponent> children;

    public IfStatement(List<AstComponent> children) {
        this.children = children;
    }

    @Override
    public List<AstComponent> getChildren() {
        return children;
    }
}
