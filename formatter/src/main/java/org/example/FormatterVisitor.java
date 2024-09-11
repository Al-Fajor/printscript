package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.ast.*;
import org.example.ast.statement.*;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ruleappliers.RuleApplier;

public class FormatterVisitor implements AstComponentVisitor<String> {
	//    TODO Create own class for rules. Verify rules immediately after getting parsed
	private final RuleProvider ruleProvider;

	public FormatterVisitor(RuleProvider ruleProvider) {
		this.ruleProvider = ruleProvider;
	}

	@Override
	public String visit(BinaryExpression expression) {
		List<String> combinedResults =
				getCombinedResults(ruleProvider.getBinaryExpressionRuleAppliers(), expression);

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
	public String visit(IfStatement ifStatement) {
		List<String> combinedResults = getCombinedResults(ruleProvider.getIfRuleAppliers(), ifStatement);
	}

	@Override
	public String visit(IfElseStatement ifElseStatement) {
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
				getCombinedResults(ruleProvider.getParameterRuleAppliers(), parameters);

		return parameters.getParameters().stream()
				.map(parameter -> parameter.accept(this))
				.collect(Collectors.joining(", "));
	}

	@Override
	public String visit(AssignmentStatement statement) {
		List<String> combinedResults =
				getCombinedResults(ruleProvider.getAssignmentRuleAppliers(), statement);

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
	public String visit(DeclarationAssignmentStatement statement) {
		AssignmentStatement assignmentStatement =
				new AssignmentStatement(
						statement.getIdentifier(),
						statement.getEvaluableComponent(),
						statement.start(),
						statement.end());
		List<String> combinedResults =
				getCombinedResults(ruleProvider.getAssignmentRuleAppliers(), assignmentStatement);

		String right = statement.getEvaluableComponent().accept(this);
		DeclarationStatement declaration =
				new DeclarationStatement(
						statement.getDeclarationType(),
						statement.getIdentifierType(),
						statement.getIdentifier(),
						statement.start(),
						statement.end());
		String left = declaration.accept(this);
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
	public String visit(DeclarationStatement statement) {
		List<String> combinedResults =
				getCombinedResults(ruleProvider.getDeclarationRuleAppliers(), statement);

		return "let "
				+ combinedResults.get(0)
				+ statement.getIdentifier().getName()
				+ combinedResults.get(1)
				+ ":"
				+ combinedResults.get(2)
				+ statement.getDeclarationType().toString().toLowerCase()
				+ combinedResults.get(3);
	}

	@Override
	public String visit(FunctionCallStatement statement) {
		List<String> combinedResults =
				getCombinedResults(ruleProvider.getFunctionRuleAppliers(), statement);

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
	public String visit(Identifier identifier) {
		return identifier.getName();
	}

	@Override
	public String visit(ReadInput readInput) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public String visit(ReadEnv readEnv) {
		throw new RuntimeException("Not implemented yet");
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
		//        TODO Check this before
		if (strings.stream().anyMatch(string -> string.equals("  "))) {
			throw new RuntimeException("Two or more consecutive spaces found");
		}
	}
}
