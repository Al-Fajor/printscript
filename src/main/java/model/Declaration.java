package model;

import java.util.List;

public class Declaration implements AstComponent, AstComposite {
    private final List<AstComponent> children;

    public Declaration(List<AstComponent> children) {
        this.children = children;
    }

    @Override
    public List<AstComponent> getChildren() {
        return children;
    }
}
