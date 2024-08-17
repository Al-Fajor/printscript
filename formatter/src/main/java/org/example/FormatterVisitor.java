package org.example;

import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.Visitor;
import org.example.factories.RuleMapFactory;

import java.util.Map;
import java.util.stream.Collectors;

public class FormatterVisitor implements Visitor<String> {

    private final Map<String, Object> ruleMap = new RuleMapFactory().getRuleMap();

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
        return literal.getValue().toString();
    }

    @Override
    public String visit(Parameters parameters) {
        return parameters.toString();
    }

    @Override
    public String visit(AssignationStatement statement) {
        boolean spaceAroundEquals = (boolean) ruleMap.get("spaceAroundEquals");
        return statement.getLeft().accept(this) +
            (spaceAroundEquals ? " " : "") +
            "=" +
            (spaceAroundEquals ? " " : "") +
            statement.getRight().accept(this);
    }

    @Override
    public String visit(Declaration statement) {
        boolean spaceBeforeColon = (boolean) ruleMap.get("spaceBeforeColon");
        boolean spaceAfterColon = (boolean) ruleMap.get("spaceAfterColon");
        return
            statement.getName() +
            (spaceBeforeColon ? " " : "" ) +
            ":" +
            (spaceAfterColon ? " " : "" ) +
            statement.getType().toString().toLowerCase();
    }

    @Override
    public String visit(FunctionCallStatement statement) {
        return statement.getLeft().accept(this) + "(" + statement.getRight().accept(this) + ")";
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
}
