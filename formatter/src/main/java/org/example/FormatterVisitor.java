package org.example;

import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.factories.RuleFactory;
import org.example.ruleappliers.RuleApplier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormatterVisitor implements AstComponentVisitor<String> {
//    TODO Create own class for rules. Verify rules immediately after getting parsed
//    TODO think of a better way to structure code following SOLID
    private final Rules rules;

    public FormatterVisitor(RuleFactory ruleFactory) {
        this.rules = ruleFactory.getRules();
    }

    @Override
    public String visit(BinaryExpression expression) {
        String left = expression.getLeftComponent().accept(this);
        String right = expression.getRightComponent().accept(this);
        return left + " " + expression.getOperator() + " " + right;
    }

    @Override
    public String visit(Conditional conditional) {
        return conditional.getCondition().accept(this);
    }

    @Override
    public String visit(IfStatement ifStatement) {
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
        return parameters.getParameters().stream()
                .map(parameter -> parameter.accept(this))
                .collect(Collectors.joining(", "));
    }

    @Override
    public String visit(AssignationStatement statement) {
        List<RuleApplier<AssignationStatement>> appliedRules = rules.getAssignationRuleAppliers();
        List<List<String>> results = appliedRules.stream()
                .map(rule -> rule.applyRules(this, statement))
                .toList();
        List<String> combinedResults = combineStringsLists(results);
        String right = statement.getRight().accept(this);
        String left = statement.getLeft().accept(this);
        if (right.isEmpty()) {
            return left;
        }
        return combinedResults.get(0) +
                left +
                combinedResults.get(1) +
            "=" +
                combinedResults.get(2) +
            right +
                combinedResults.get(3);
    }

    @Override
    public String visit(Declaration statement) {
        List<RuleApplier<Declaration>> appliedRules = rules.getDeclarationRuleAppliers();
        List<List<String>> results = appliedRules.stream()
                .map(rule -> rule.applyRules(this, statement))
                .toList();
        List<String> combinedResults = combineStringsLists(results);
        return
            "let " +
            combinedResults.get(0) +
            statement.getName() +
            combinedResults.get(1) +
            ":" +
            combinedResults.get(2) +
            statement.getType().toString().toLowerCase() +
            combinedResults.get(3);
    }

    @Override
    public String visit(FunctionCallStatement statement) {
        List<RuleApplier<FunctionCallStatement>> appliedRules = rules.getFunctionRuleAppliers();
        List<List<String>> results = appliedRules.stream()
                .map(rule -> rule.applyRules(this, statement))
                .toList();
        List<String> combinedResults = combineStringsLists(results);
        return  combinedResults.get(0) +
                statement.getLeft().accept(this) +
                combinedResults.get(1) +
                "(" +
                combinedResults.get(2) +
                statement.getRight().accept(this) +
                combinedResults.get(3) +
                ")" +
                combinedResults.get(4);
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
        for (int i = 0; i < listOfLists.getFirst().size(); i++) {
            result.add(addAllNthElements(listOfLists, i));
        }
        return result;
    }

    private String addAllNthElements(List<List<String>> listOfLists, int n) {
        return listOfLists.stream()
                .map(list -> list.get(n))
                .collect(Collectors.joining());
    }
}
