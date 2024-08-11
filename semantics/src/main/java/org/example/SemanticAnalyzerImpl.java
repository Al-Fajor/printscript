package org.example;

import org.example.ast.AstComponent;
import org.example.ast.BinaryExpression;
import org.example.ast.BinaryOperator;
import org.example.ast.Conditional;
import org.example.ast.Declaration;
import org.example.ast.DeclarationType;
import org.example.ast.Literal;
import org.example.ast.Parameters;
import org.example.ast.StatementBlock;
import org.example.ast.VariableIdentifier;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.resolution_validators.AssigningToValidValue;
import org.example.resolution_validators.IdentifierExists;
import org.example.resolution_validators.IsDeclarationElseAssignation;
import org.example.resolution_validators.IsSimpleDeclaration;
import org.example.resolution_validators.IsSuccessful;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SemanticAnalyzerImpl implements SemanticAnalyzer {
    // TODO: may define externally, such as in a config file
    private final Environment baseEnvironment;
    private Environment env;

    public SemanticAnalyzerImpl(Environment baseEnvironment) {
        this.baseEnvironment = baseEnvironment;
    }

    @Override
    public SemanticResult analyze(List<AstComponent> asts) {
        env = baseEnvironment.copy();
        
        for (AstComponent ast : asts) {
            var resolution = ast.accept(this);
            if (!resolution.result().isSuccessful()) return resolution.result();
        }

        return new SemanticSuccess();
    }

    @Override
    public Resolution visit(BinaryExpression expression) {
        var leftResolution = expression.getLeftComponent().accept(this);

        if (!leftResolution.result().isSuccessful()) {
            return leftResolution;
        }
        var rightResolution = expression.getRightComponent().accept(this);

        if (!rightResolution.result().isSuccessful()) {
            return rightResolution;
        }

        if (expression.getOperator() == BinaryOperator.SUM) {
            if (bothAreNumbers(leftResolution, rightResolution)) {
                return new Resolution(
                        new SemanticSuccess(),
                        Optional.of(DeclarationType.NUMBER),
                        true,
                        Optional.empty(),
                        Collections.emptySet()
                );
            } else {
                return new Resolution(
                        new SemanticSuccess(),
                        Optional.of(DeclarationType.STRING),
                        true,
                        Optional.empty(),
                        Collections.emptySet()
                );
            }
        }

        else {
            if (differentTypes(rightResolution, leftResolution)) {
                return Resolution.failure(
                        "Cannot perform operation because types are incompatible: "
                                + rightResolution.evaluatedType().get() + " "
                                + getSymbol(expression.getOperator()) + " "
                                + leftResolution.evaluatedType().get() + " "
                );
            } else {
                return new Resolution(
                        new SemanticSuccess(),
                        Optional.of(rightResolution.evaluatedType().get()),
                        true,
                        Optional.empty(),
                        Collections.emptySet()
                );
            }
        }
    }

    private String getSymbol(BinaryOperator operator) {
        // TODO: could move to BinaryOperator
        switch (operator) {
            case SUM:
                return "+";
            case SUBTRACTION:
                return "-";
            case MULTIPLICATION:
                return "*";
            case DIVISION:
                return "/";
            default:
                // Unreachable
                throw new RuntimeException("Invalid operator: " + operator + "(Resolution return for this type yet to be implemented)");
        }
    }

    private static boolean differentTypes(Resolution rightResolution, Resolution leftResolution) {
        return rightResolution.evaluatedType().get() != leftResolution.evaluatedType().get();
    }

    private static boolean bothAreNumbers(Resolution rightResolution, Resolution leftResolution) {
        return rightResolution.evaluatedType().get() == DeclarationType.NUMBER
                && leftResolution.evaluatedType().get() == DeclarationType.NUMBER;
    }

    @Override
    public Resolution visit(Conditional conditional) {
        return null;
    }

    @Override
    public Resolution visit(IfStatement ifStatement) {
        return null;
    }

    @Override
    public Resolution visit(Literal<?> literal) {
        return new Resolution(
                new SemanticSuccess(),
                Optional.of(mapToDeclarationType(literal)),
                true,
                Optional.empty(),
                Collections.emptySet()
        );
    }

    private DeclarationType mapToDeclarationType(Literal<?> literal) {
        return switch(literal.getValue()) {
            case String ignoredString -> DeclarationType.STRING;
            case Number ignoredNumber -> DeclarationType.NUMBER;
            default -> throw new IllegalStateException("Unexpected value: " + literal.getValue());
        };
    }

    @Override
    public Resolution visit(Parameters parameters) {
        return null;
    }

    @Override
    public Resolution visit(AssignationStatement statement) {
        Resolution leftResolution = statement.getLeft().accept(this);
        Resolution rightResolution = statement.getRight().accept(this);

        return new IsSuccessful(
                rightResolution,
                new IsDeclarationElseAssignation(
                        leftResolution,
                        new IdentifierExists(
                                leftResolution,
                                (env) -> Resolution.failure("Variable has already been declared"),
                                new IsSimpleDeclaration(
                                        rightResolution,
                                        (env) -> {
                                            env.declareVariable(leftResolution.identifierName().get(), leftResolution.evaluatedType().get());
                                            return Resolution.success(leftResolution.resolvedDeclarations());
                                        },
                                        new AssigningToValidValue(
                                                leftResolution,
                                                rightResolution,
                                                (env) -> {
                                                    env.declareVariable(leftResolution.identifierName().get(), leftResolution.evaluatedType().get());
                                                    return Resolution.success(leftResolution.resolvedDeclarations());
                                                },
                                                (env) -> Resolution.failure(
                                                        "Cannot assign type " + rightResolution.evaluatedType().get()
                                                                + " to " + leftResolution.evaluatedType().get()
                                                )
                                        )

                                )
                        ),
                        new IdentifierExists(
                                leftResolution,
                                new AssigningToValidValue(
                                        leftResolution,
                                        rightResolution,
                                        (env) -> Resolution.success(leftResolution.resolvedDeclarations()),
                                        (env) -> Resolution.failure(
                                                "Cannot assign type " + rightResolution.evaluatedType().get()
                                                        + " to " + leftResolution.evaluatedType().get()
                                        )
                                ),
                                (env) -> Resolution.failure("Cannot assign non-existing identifier")
                        )
                ),
                (env) -> rightResolution
        ).analyze(env);
    }

    @Override
    public Resolution visit(Declaration statement) {
        return new Resolution(
                new SemanticSuccess(),
                Optional.of(statement.getType()),
                false,
                Optional.of(statement.getName()),
                Set.of(statement)
        );
    }

    @Override
    public Resolution visit(FunctionCallStatement statement) {
        return null;
    }

    @Override
    public Resolution visit(StatementBlock statementBlock) {
        return null;
    }

    @Override
    public Resolution visit(VariableIdentifier variableIdentifier) {
        // TODO: convert to validator
        if (!env.isVariableDeclared(variableIdentifier.getName())) {
            return new Resolution(
                    new SemanticFailure("Cannot find identifier " + variableIdentifier.getName()),
                    Optional.empty(),
                    false,
                    Optional.of(variableIdentifier.getName()),
                    Collections.emptySet()
            );
        }
        else {
            return new Resolution(
                    new SemanticSuccess(),
                    Optional.of(env.getDeclarationType(variableIdentifier.getName())),
                    false,
                    Optional.of(variableIdentifier.getName()),
                    Collections.emptySet()
            );
        }
    }
}
