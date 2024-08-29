package org.example.ruleappliers.parameters;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.Parameters;
import org.example.ast.StatementBlock;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class SpacesAroundCommasInParameters implements RuleApplier<Parameters> {
	private final boolean spacesAroundCommas;

	public SpacesAroundCommasInParameters(boolean spacesAroundCommas) {
		this.spacesAroundCommas = spacesAroundCommas;
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.PARAMETERS;
	}

	@Override
	public boolean isApplicable(AstComponent component) {
		return component instanceof StatementBlock;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, Parameters parameters) {
		return List.of("", "", (spacesAroundCommas ? " " : ""), "");
	}
}
