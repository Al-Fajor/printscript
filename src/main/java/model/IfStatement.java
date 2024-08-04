package model;

public class IfStatement implements AstComponent {
    private final Conditional conditional;
    private final AstComponent trueClause;
    private final AstComponent falseClause;

    public IfStatement(Conditional conditional, AstComponent trueClause, AstComponent falseClause) {
        this.conditional = conditional;
        this.trueClause = trueClause;
        this.falseClause = falseClause;
    }

    public Conditional getConditional() {
        return conditional;
    }

    public AstComponent getFalseClause() {
        return falseClause;
    }

    public AstComponent getTrueClause() {
        return trueClause;
    }
}
