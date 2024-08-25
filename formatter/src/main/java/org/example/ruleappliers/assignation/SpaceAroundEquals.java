package org.example.ruleappliers.assignation;

import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.statement.AssignationStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

import java.util.List;

public class SpaceAroundEquals implements RuleApplier<AssignationStatement> {
    private boolean spaceAroundEquals;

    public SpaceAroundEquals(boolean spaceAroundEquals) {
        this.spaceAroundEquals = spaceAroundEquals;
    }

    @Override
    public List<String> applyRules(FormatterVisitor visitor, AssignationStatement statement) {
        return List.of(
                "",
                (spaceAroundEquals ? " " : ""),
                (spaceAroundEquals ? " " : ""),
                ""
        );
    }

    @Override
    public RuleApplierTypes getType() {
        return RuleApplierTypes.ASSIGNATION;
    }

    @Override
    public boolean isApplicable(AstComponent component) {
        return component instanceof AssignationStatement;
    }
}
