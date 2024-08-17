package org.example;

import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.factories.RuleMapFactory;

import java.util.Map;
import java.util.stream.Collectors;

public class FormatterVisitor implements AstComponentVisitor<String> {

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
//        TODO maybe change this idk
        boolean spaceAroundEquals = (boolean) ruleMap.get("spaceAroundEquals");
        String right = statement.getRight().accept(this);
        String left = statement.getLeft().accept(this);
        if (right.equals("")) {
            return left;
        }
        return left +
            (spaceAroundEquals ? " " : "") +
            "=" +
            (spaceAroundEquals ? " " : "") +
            right;
    }

    @Override
    public String visit(Declaration statement) {
//        TODO maybe change this idk
        boolean spaceBeforeColon = (boolean) ruleMap.get("spaceBeforeColon");
        boolean spaceAfterColon = (boolean) ruleMap.get("spaceAfterColon");
        return
            "let " +
            statement.getName() +
            (spaceBeforeColon ? " " : "" ) +
            ":" +
            (spaceAfterColon ? " " : "" ) +
            statement.getType().toString().toLowerCase();
    }

    @Override
    public String visit(FunctionCallStatement statement) {
        int spacesBeforePrintln = (int) ruleMap.get("spaceBeforePrintln");
        String spaces = " ".repeat(spacesBeforePrintln);
        return spaces + statement.getLeft().accept(this) + "(" + statement.getRight().accept(this) + ")";
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
