package org.example.evaluables;

import org.example.Environment;
import org.example.SemanticFailure;
import org.example.SemanticSuccess;
import org.example.ast.BinaryExpression;
import org.example.ast.BinaryOperator;
import org.example.ast.Conditional;
import org.example.ast.Declaration;
import org.example.ast.DeclarationType;
import org.example.ast.Identifier;
import org.example.ast.Literal;
import org.example.ast.Parameters;
import org.example.ast.StatementBlock;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.Visitor;
import org.example.externalization.Language;
import org.example.identifiers.IdentifierResolution;
import org.example.identifiers.IdentifierVisitor;
import org.example.parameters.ParametersResolution;
import org.example.parameters.ParametersVisitor;
import org.example.resolution_validators.AssigningToValidValue;
import org.example.resolution_validators.IdentifierExists;
import org.example.resolution_validators.IsDeclarationElseAssignation;
import org.example.resolution_validators.IsOperationValid;
import org.example.resolution_validators.IsSimpleDeclaration;
import org.example.resolution_validators.IsSuccessful;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class EvaluableVisitor implements Visitor<Resolution> {
    private Environment env;
    private final IdentifierVisitor identifierVisitor;
    private final ParametersVisitor parametersVisitor;
    private final Language lang = new Language();

    public EvaluableVisitor(Environment env, IdentifierVisitor identifierVisitor, ParametersVisitor parametersVisitor) {
        this.env = env;
        this.identifierVisitor = identifierVisitor;
        this.parametersVisitor = parametersVisitor;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    @Override
    public Resolution visit(BinaryExpression expression) {
        var leftResolution = expression.getLeftComponent().accept(this);
        var rightResolution = expression.getRightComponent().accept(this);

        return new IsSuccessful(
                leftResolution,
                new IsSuccessful(
                        rightResolution,
                            new IsOperationValid(
                                    lang,
                                    leftResolution,
                                    rightResolution,
                                    expression,
                                    (env) -> new Resolution(
                                            new SemanticSuccess(),
                                            Optional.of(lang.getResolvedType(
                                                    leftResolution.evaluatedType().get(),
                                                    expression.getOperator(),
                                                    rightResolution.evaluatedType().get())
                                            ),
                                            true,
                                            Optional.empty()
                                    ),
                                    (env) -> Resolution.emptyFailure(
                                            "Cannot perform operation because types are incompatible: "
                                                    + rightResolution.evaluatedType().get() + " "
                                                    + getSymbol(expression.getOperator()) + " "
                                                    + leftResolution.evaluatedType().get() + " "

                                    )
                            ),
                        (env) -> rightResolution
                ),
                (env) -> leftResolution
        ).analyze(env);
    }

    private String getSymbol(BinaryOperator operator) {
        // TODO: could move to BinaryOperator
        return switch (operator) {
            case SUM -> "+";
            case SUBTRACTION -> "-";
            case MULTIPLICATION -> "*";
            case DIVISION -> "/";
            default ->
                    throw new RuntimeException("Invalid operator: " + operator + "(Resolution return for this type yet to be implemented)");
        };
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
                Optional.empty()
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
        IdentifierResolution leftResolution = statement.getLeft().accept(identifierVisitor);
        Resolution rightResolution = statement.getRight().accept(this);

        return new IsSuccessful(
                rightResolution,
                new IsDeclarationElseAssignation(
                        leftResolution,
                        new IdentifierExists(
                                leftResolution,
                                (env) -> Resolution.emptyFailure("Variable has already been declared"),
                                new IsSimpleDeclaration(
                                        rightResolution,
                                        (env) -> {
                                            env.declareVariable(leftResolution.name(), leftResolution.type().get());
                                            return Resolution.emptySuccess();
                                        },
                                        new AssigningToValidValue(
                                                leftResolution,
                                                rightResolution,
                                                (env) -> {
                                                    env.declareVariable(leftResolution.name(), leftResolution.type().get());
                                                    return Resolution.emptySuccess();
                                                },
                                                (env) -> Resolution.emptyFailure(
                                                        "Cannot assign value of type " + rightResolution.evaluatedType().get()
                                                                + " to variable of type" + leftResolution.type().get()
                                                )
                                        )

                                )
                        ),
                        new IdentifierExists(
                                leftResolution,
                                new AssigningToValidValue(
                                        leftResolution,
                                        rightResolution,
                                        (env) -> Resolution.emptySuccess(),
                                        (env) -> Resolution.emptyFailure(
                                                "Cannot assign type " + rightResolution.evaluatedType().get()
                                                        + " to " + env.getDeclarationType(leftResolution.name())
                                        )
                                ),
                                (env) -> Resolution.emptyFailure("Cannot assign non-existing identifier")
                        )
                ),
                (env) -> rightResolution
        ).analyze(env);
    }

    @Override
    public Resolution visit(Declaration statement) {
        return null;
    }

    @Override
    public Resolution visit(FunctionCallStatement statement) {
        IdentifierResolution functionCallResolution = statement.getLeft().accept(identifierVisitor);
        ParametersResolution parameterResolution = statement.getRight().accept(parametersVisitor);

        if (!functionCallResolution.result().isSuccessful()) {
            return Resolution.emptyFailure(functionCallResolution.result().errorMessage());
        }

        if (!parameterResolution.result().isSuccessful()) {
            return Resolution.emptyFailure(parameterResolution.result().errorMessage());
        }

        List<DeclarationType> types = parameterResolution.types();

        String functionName = functionCallResolution.name();
        if (!env.isFunctionDeclared(functionName, types)) {
            return Resolution.emptyFailure("Cannot resolve " + functionName + "(" + types + ").");
        }
        // TODO: resolve into Literal if not void
        return Resolution.emptySuccess();
    }

    @Override
    public Resolution visit(StatementBlock statementBlock) {
        return null;
    }

    @Override
    public Resolution visit(Identifier identifier) {
        // TODO: convert to validator
        if (!env.isVariableDeclared(identifier.getName())) {
            return new Resolution(
                    new SemanticFailure("Cannot find identifier " + identifier.getName()),
                    Optional.empty(),
                    false,
                    Optional.of(identifier.getName())
            );
        }
        else {
            return new Resolution(
                    new SemanticSuccess(),
                    Optional.of(env.getDeclarationType(identifier.getName())),
                    false,
                    Optional.of(identifier.getName())
            );
        }
    }
}
