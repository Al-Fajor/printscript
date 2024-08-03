package model;

import java.util.List;

public class Assignation implements AstComponent, AstComposite {
    private final List<AstComponent> children;

    public Assignation(List<AstComponent> children) {
        this.children = children;
    }

    @Override
    public List<AstComponent> getChildren() {
        return children;
    }
}
