package model;

import java.util.List;

public class BlockStatement implements AstComponent, AstComposite {
    private final List<AstComponent> children;

    public BlockStatement(List<AstComponent> children) {
        this.children = children;
    }

    @Override
    public List<AstComponent> getChildren() {
        return children;
    }
}
