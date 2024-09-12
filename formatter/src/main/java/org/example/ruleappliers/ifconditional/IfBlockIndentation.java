package org.example.ruleappliers.ifconditional;

import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.statement.IfStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

import java.util.List;

public class IfBlockIndentation implements RuleApplier<IfStatement> {
    private final int spaces;

    public IfBlockIndentation (int spaces) {
        this.spaces = spaces;
    }

    @Override
    public RuleApplierTypes getType() {
        return RuleApplierTypes.IF;
    }

    @Override
    public boolean isApplicable(AstComponent component) {
        return component instanceof IfStatement;
    }

    @Override
    public List<String> applyRules(FormatterVisitor visitor, IfStatement statement) {
        return List.of("", "", "", "", "", " ".repeat(spaces), "");
    }
}
