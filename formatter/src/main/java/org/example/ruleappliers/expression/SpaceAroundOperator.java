package org.example.ruleappliers.expression;

import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.BinaryExpression;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

import java.util.List;

public class SpaceAroundOperator implements RuleApplier<BinaryExpression> {
    private final boolean spaceAroundOperator;

    public SpaceAroundOperator(boolean spaceAroundOperator) {
        this.spaceAroundOperator = spaceAroundOperator;
    }

    @Override
    public RuleApplierTypes getType() {
        return RuleApplierTypes.BINARY_EXPRESSION;
    }

    @Override
    public boolean isApplicable(AstComponent component) {
        return component instanceof BinaryExpression;
    }

    @Override
    public List<String> applyRules(FormatterVisitor visitor, BinaryExpression statement) {
        return List.of(
                "",
                (spaceAroundOperator ? " " : ""),
                (spaceAroundOperator ? " " : ""),
                ""
        );
    }
}
