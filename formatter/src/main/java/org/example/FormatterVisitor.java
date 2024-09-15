package org.example;

import java.util.*;
import java.util.stream.Collectors;
import org.example.ast.*;
import org.example.ast.statement.*;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.assignation.AssignationSpaces;
import org.example.ruleappliers.declaration.DeclarationSpaces;
import org.example.ruleappliers.expression.ExpressionSpaces;
import org.example.ruleappliers.function.FunctionSpaces;
import org.example.ruleappliers.ifconditional.IfSpaces;
import org.example.ruleappliers.ifelse.IfElseSpaces;
import org.example.ruleappliers.parameters.ParametersSpaces;
import org.example.ruleappliers.readenv.ReadEnvSpaces;
import org.example.ruleappliers.readinput.ReadInputSpaces;

public class FormatterVisitor implements AstComponentVisitor<String> {
	private final RuleProvider ruleProvider;

	public FormatterVisitor(RuleProvider ruleProvider) {
		this.ruleProvider = ruleProvider;
	}

	@Override
	public String visit(BinaryExpression expression) {
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getBinaryExpressionRuleAppliers(), expression);

		String left = expression.getLeftComponent().accept(this);
		String right = expression.getRightComponent().accept(this);

		return combinedResults.get(ExpressionSpaces.SPACE_BEFORE_EXPRESSION)
				+ left
				+ combinedResults.get(ExpressionSpaces.SPACE_BEFORE_OPERATOR)
				+ expression.getOperator()
				+ combinedResults.get(ExpressionSpaces.SPACE_AFTER_OPERATOR)
				+ right
				+ combinedResults.get(ExpressionSpaces.SPACE_AFTER_EXPRESSION);
	}

	@Override
	public String visit(IfStatement ifStatement) {
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getIfRuleAppliers(), ifStatement);

		return combinedResults.get(IfSpaces.SPACES_BEFORE_IF)
				+ "if"
				+ combinedResults.get(IfSpaces.SPACES_AFTER_IF)
				+ "("
				+ combinedResults.get(IfSpaces.SPACES_BEFORE_CONDITION)
				+ ifStatement.conditionalIdentifier().accept(this)
				+ combinedResults.get(IfSpaces.SPACES_AFTER_CONDITION)
				+ ")"
				+ combinedResults.get(IfSpaces.SPACES_BEFORE_BLOCK)
				+ "{"
				+ combinedResults.get(IfSpaces.SPACES_IN_BLOCK_START)
				+ buildClauseStatements(
						ifStatement.trueClause().iterator(),
						combinedResults.get(IfSpaces.INDENTATION_IN_BLOCK))
				+ combinedResults.get(IfSpaces.SPACES_IN_BLOCK_END)
				+ "}"
				+ combinedResults.get(IfSpaces.SPACES_AFTER_BLOCK);
	}

	private String buildClauseStatements(Iterator<Statement> statements, String spaces) {
		StringBuilder clauseStatements = new StringBuilder();
		clauseStatements.append("\n");
		while (statements.hasNext()) {
			Statement statement = statements.next();
			clauseStatements.append(spaces).append(statement.accept(this));
			if (clauseStatements.charAt(clauseStatements.length() - 1) != '}') {
				clauseStatements.append(";");
			}
			clauseStatements.append("\n");
		}
		return clauseStatements.toString();
	}

	@Override
	public String visit(IfElseStatement ifElseStatement) {
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getIfElseRuleAppliers(), ifElseStatement);

		return combinedResults.get(IfElseSpaces.SPACES_BEFORE_IF)
				+ "if"
				+ combinedResults.get(IfElseSpaces.SPACES_AFTER_IF)
				+ "("
				+ combinedResults.get(IfElseSpaces.SPACES_BEFORE_CONDITION)
				+ ifElseStatement.conditionalIdentifier().accept(this)
				+ combinedResults.get(IfElseSpaces.SPACES_AFTER_CONDITION)
				+ ")"
				+ combinedResults.get(IfElseSpaces.SPACES_BEFORE_IF_BLOCK)
				+ "{"
				+ combinedResults.get(IfElseSpaces.SPACES_IN_IF_BLOCK_START)
				+ buildClauseStatements(
						ifElseStatement.trueClause().iterator(),
						combinedResults.get(IfElseSpaces.INDENTATION_IN_IF_BLOCK))
				+ combinedResults.get(IfElseSpaces.SPACES_IN_IF_BLOCK_END)
				+ "}"
				+ combinedResults.get(IfElseSpaces.SPACES_AFTER_IF_BLOCK)
				+ "else"
				+ combinedResults.get(IfElseSpaces.SPACES_BEFORE_ELSE_BLOCK)
				+ "{"
				+ combinedResults.get(IfElseSpaces.SPACES_IN_ELSE_BLOCK_START)
				+ buildClauseStatements(
						ifElseStatement.falseClause().iterator(),
						combinedResults.get(IfElseSpaces.INDENTATION_ELSE_IN_BLOCK))
				+ combinedResults.get(IfElseSpaces.SPACES_IN_ELSE_BLOCK_END)
				+ "}"
				+ combinedResults.get(IfElseSpaces.SPACES_AFTER_ELSE_BLOCK);
	}

	@Override
	public String visit(Literal<?> literal) {
		return getStringValue(literal);
	}

	@Override
	public String visit(Parameters parameters) {
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getParameterRuleAppliers(), parameters);

		return parameters.getParameters().stream()
				.map(parameter -> parameter.accept(this))
				.collect(
						Collectors.joining(
								combinedResults.get(ParametersSpaces.SPACES_BEFORE_COMMAS)
										+ ","
										+ combinedResults.get(
												ParametersSpaces.SPACES_AFTER_COMMAS)));
	}

	@Override
	public String visit(AssignmentStatement statement) {
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getAssignmentRuleAppliers(), statement);

		String right = statement.getEvaluableComponent().accept(this);
		String left = statement.getIdentifier().accept(this);
		if (right.isEmpty()) {
			return left;
		}
		return combinedResults.get(AssignationSpaces.SPACE_BEFORE_ASSIGNATION)
				+ left
				+ combinedResults.get(AssignationSpaces.SPACE_BEFORE_EQUALS)
				+ "="
				+ combinedResults.get(AssignationSpaces.SPACE_AFTER_EQUALS)
				+ right
				+ combinedResults.get(AssignationSpaces.SPACE_AFTER_ASSIGNATION);
	}

	@Override
	public String visit(DeclarationAssignmentStatement statement) {
		AssignmentStatement assignmentStatement = getAssignmentStatement(statement);
		String right = statement.getEvaluableComponent().accept(this);
		DeclarationStatement declaration = getDeclarationStatement(statement);
		String left = declaration.accept(this);
		if (right.isEmpty()) {
			return left;
		}
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getAssignmentRuleAppliers(), assignmentStatement);
		return left
				+ combinedResults.get(AssignationSpaces.SPACE_BEFORE_EQUALS)
				+ "="
				+ combinedResults.get(AssignationSpaces.SPACE_AFTER_EQUALS)
				+ right
				+ combinedResults.get(AssignationSpaces.SPACE_AFTER_ASSIGNATION);
	}

	@Override
	public String visit(DeclarationStatement statement) {
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getDeclarationRuleAppliers(), statement);

		return combinedResults.get(DeclarationSpaces.SPACE_BEFORE_IDENTIFIER_TYPE)
				+ statement.getIdentifierType().toString().toLowerCase()
				+ combinedResults.get(DeclarationSpaces.SPACE_AFTER_IDENTIFIER_TYPE)
				+ statement.getIdentifier().getName()
				+ combinedResults.get(DeclarationSpaces.SPACE_BEFORE_COLON)
				+ ":"
				+ combinedResults.get(DeclarationSpaces.SPACE_AFTER_COLON)
				+ statement.getDeclarationType().toString().toLowerCase()
				+ combinedResults.get(DeclarationSpaces.SPACE_AFTER_DECLARATION);
	}

	@Override
	public String visit(FunctionCallStatement statement) {
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getFunctionRuleAppliers(), statement);

		return combinedResults.get(FunctionSpaces.SPACE_BEFORE_FUNCTION_IDENTIFIER)
				+ statement.getIdentifier().accept(this)
				+ combinedResults.get(FunctionSpaces.SPACE_AFTER_FUNCTION_IDENTIFIER)
				+ "("
				+ combinedResults.get(FunctionSpaces.SPACE_BEFORE_FUNCTION_PARAMETERS)
				+ statement.getParameters().accept(this)
				+ combinedResults.get(FunctionSpaces.SPACE_AFTER_FUNCTION_PARAMETERS)
				+ ")"
				+ combinedResults.get(FunctionSpaces.SPACE_AFTER_FUNCTION_CALL);
	}

	@Override
	public String visit(Identifier identifier) {
		return identifier.getName();
	}

	@Override
	public String visit(ReadInput readInput) {
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getReadInputRuleAppliers(), readInput);

		return combinedResults.get(ReadInputSpaces.SPACE_BEFORE_READINPUT_IDENTIFIER)
				+ "readInput"
				+ combinedResults.get(ReadInputSpaces.SPACE_AFTER_READINPUT_IDENTIFIER)
				+ "("
				+ combinedResults.get(ReadInputSpaces.SPACE_BEFORE_READINPUT_PARAMETERS)
				+ readInput.getMessage()
				+ combinedResults.get(ReadInputSpaces.SPACE_AFTER_READINPUT_PARAMETERS)
				+ ")"
				+ combinedResults.get(ReadInputSpaces.SPACE_AFTER_READINPUT_CALL);
	}

	@Override
	public String visit(ReadEnv readEnv) {
		Map<ApplicableSpaces, String> combinedResults =
				getCombinedResults(ruleProvider.getReadEnvRuleAppliers(), readEnv);
		return combinedResults.get(ReadEnvSpaces.SPACE_BEFORE_READENV_IDENTIFIER)
				+ "readEnv"
				+ combinedResults.get(ReadEnvSpaces.SPACE_AFTER_READENV_IDENTIFIER)
				+ "("
				+ combinedResults.get(ReadEnvSpaces.SPACE_BEFORE_READENV_PARAMETERS)
				+ readEnv.getVariableName()
				+ combinedResults.get(ReadEnvSpaces.SPACE_AFTER_READENV_PARAMETERS)
				+ ")"
				+ combinedResults.get(ReadEnvSpaces.SPACE_AFTER_READENV_CALL);
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

	private <T extends AstComponent> Map<ApplicableSpaces, String> getCombinedResults(
			List<RuleApplier<T>> appliedRules, T statement) {
		Map<ApplicableSpaces, String> newMap = new HashMap<>();
		for (ApplicableSpaces key : getBaseRulesKeys(appliedRules, statement)) {
			String value =
					appliedRules.stream()
							.map(ruleApplier -> ruleApplier.applyRules(this, statement).get(key))
							.reduce("", (acc, str) -> acc + str);
			hasNoTwoOrMoreConsecutiveSpaces(value);
			newMap.put(key, value);
		}
		return newMap;
	}

	private void hasNoTwoOrMoreConsecutiveSpaces(String string) {
		if (string.matches(" {2}")) {
			throw new IllegalArgumentException("Two or more consecutive spaces found");
		}
	}

	private <T extends AstComponent> Set<ApplicableSpaces> getBaseRulesKeys(
			List<RuleApplier<T>> ruleAppliers, T statement) {
		return ruleAppliers.getFirst().applyRules(this, statement).keySet();
	}

	private DeclarationStatement getDeclarationStatement(DeclarationAssignmentStatement statement) {
		return new DeclarationStatement(
				statement.getDeclarationType(),
				statement.getIdentifierType(),
				statement.getIdentifier(),
				statement.start(),
				statement.end());
	}

	private AssignmentStatement getAssignmentStatement(DeclarationAssignmentStatement statement) {
		return new AssignmentStatement(
				statement.getIdentifier(),
				statement.getEvaluableComponent(),
				statement.start(),
				statement.end());
	}

	private String getStringValue(Literal<?> literal) {
		if (literal.getValue() == null) {
			return "";
		}
		if (literal.getValue() instanceof String) {
			return "\"" + literal.getValue() + "\"";
		}
		return literal.getValue().toString();
	}
}
