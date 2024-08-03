package model;

public class IfStatement implements AstComponent {
    private final AstComponent conditional;
    private final AstComponent trueClause;
    private final AstComponent falseClause;

    public IfStatement(AstComponent conditional, AstComponent trueClause, AstComponent falseClause) {
        this.conditional = conditional;
        this.trueClause = trueClause;
        this.falseClause = falseClause;
    }

    public AstComponent getConditional() {
        return conditional;
    }

    public AstComponent getFalseClause() {
        return falseClause;
    }

    public AstComponent getTrueClause() {
        return trueClause;
    }
}
