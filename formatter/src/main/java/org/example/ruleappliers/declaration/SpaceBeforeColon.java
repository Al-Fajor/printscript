package org.example.ruleappliers.declaration;

import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.Declaration;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

import java.util.List;

public class SpaceBeforeColon implements RuleApplier<Declaration> {
    private final boolean spaceBeforeColon;

    public SpaceBeforeColon(boolean spaceBeforeColon) {
        this.spaceBeforeColon = spaceBeforeColon;
    }

    @Override
    public List<String> applyRules(FormatterVisitor visitor, Declaration declaration) {
        return List.of(
                "",
                (spaceBeforeColon ? " " : ""),
                "",
                ""
        );
    }

    @Override
    public RuleApplierTypes getType() {
        return RuleApplierTypes.DECLARATION;
    }

    @Override
    public boolean isApplicable(AstComponent component) {
        return component instanceof Declaration;
    }
}
