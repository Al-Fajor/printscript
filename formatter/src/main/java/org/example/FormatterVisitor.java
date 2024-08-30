package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.factories.RuleFactory;
import org.example.ruleappliers.RuleApplier;

public class FormatterVisitor implements AstComponentVisitor<String> {
	//    TODO Create own class for rules. Verify rules immediately after getting parsed
	private final FormatterRules formatterRules;

	public FormatterVisitor(RuleFactory ruleFactory) {
		this.formatterRules = ruleFactory.getRules();
	}

	@Override
	public String visit(BinaryExpression expression) {
		List<String> combinedResults =
				getCombinedResults(formatterRules.getBinaryExpressionRuleAppliers(), expression);

		String left = expression.getLeftComponent().accept(this);
		String right = expression.getRightComponent().accept(this);

		return combinedResults.get(0)
				+ left
				+ combinedResults.get(1)
				+ expression.getOperator()
				+ combinedResults.get(2)
				+ right
				+ combinedResults.get(3);
	}

	@Override
	public String visit(Conditional conditional) {
		return conditional.getCondition().accept(this);
	}

	@Override
	public String visit(IfStatement ifStatement) {
		//        TODO implement
		return "";
	}

	@Override
	public String visit(Literal<?> literal) {
		if (literal.getValue() == null) {
			return "";
		}
		//        TODO remove instanceof
		if (literal.getValue() instanceof String) {
			return "\"" + literal.getValue() + "\"";
		}
		return literal.getValue().toString();
	}

	@Override
	public String visit(Parameters parameters) {
		List<String> combinedResults =
				getCombinedResults(formatterRules.getParameterRuleAppliers(), parameters);

		return parameters.getParameters().stream()
				.map(parameter -> parameter.accept(this))
				.collect(Collectors.joining(", "));
	}

	@Override
	public String visit(AssignationStatement statement) {
		List<String> combinedResults =
				getCombinedResults(formatterRules.getAssignationRuleAppliers(), statement);

		String right = statement.getEvaluableComponent().accept(this);
		String left = statement.getIdentifier().accept(this);
		if (right.isEmpty()) {
			return left;
		}
		return combinedResults.get(0)
				+ left
				+ combinedResults.get(1)
				+ "="
				+ combinedResults.get(2)
				+ right
				+ combinedResults.get(3);
	}

	@Override
	public String visit(Declaration statement) {
		List<String> combinedResults =
				getCombinedResults(formatterRules.getDeclarationRuleAppliers(), statement);

		return "let "
				+ combinedResults.get(0)
				+ statement.getName()
				+ combinedResults.get(1)
				+ ":"
				+ combinedResults.get(2)
				+ statement.getType().toString().toLowerCase()
				+ combinedResults.get(3);
	}

	@Override
	public String visit(FunctionCallStatement statement) {
		List<String> combinedResults =
				getCombinedResults(formatterRules.getFunctionRuleAppliers(), statement);

		return combinedResults.get(0)
				+ statement.getIdentifier().accept(this)
				+ combinedResults.get(1)
				+ "("
				+ combinedResults.get(2)
				+ statement.getParameters().accept(this)
				+ combinedResults.get(3)
				+ ")"
				+ combinedResults.get(4);
	}

	@Override
	public String visit(StatementBlock statementBlock) {
		return statementBlock.getStatements().stream()
				.map(statement -> statement.accept(this))
				.collect(Collectors.joining(", "));
	}

	@Override
	public String visit(Identifier identifier) {
		return identifier.getName();
	}

	private List<String> combineStringsLists(List<List<String>> listOfLists) {
		List<String> result = new ArrayList<>();
		if (listOfLists.isEmpty()) {
			return result;
		}
		for (int i = 0; i < listOfLists.getFirst().size(); i++) {
			result.add(addAllNthElements(listOfLists, i));
		}
		return result;
	}

	private String addAllNthElements(List<List<String>> listOfLists, int n) {
		return listOfLists.stream().map(list -> list.get(n)).collect(Collectors.joining());
	}

	private <T extends AstComponent> List<String> getCombinedResults(
			List<RuleApplier<T>> appliedRules, T statement) {
		List<List<String>> results =
				appliedRules.stream().map(rule -> rule.applyRules(this, statement)).toList();
		hasNoTwoOrMoreConsecutiveSpaces(combineStringsLists(results));
		return combineStringsLists(results);
	}

	private void hasNoTwoOrMoreConsecutiveSpaces(List<String> strings) {
		if (strings.stream().anyMatch(string -> string.equals("  "))) {
			throw new RuntimeException("Two or more consecutive spaces found");
		}
	}
}
