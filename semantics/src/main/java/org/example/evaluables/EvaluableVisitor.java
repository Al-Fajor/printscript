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
import org.example.resolution_validators.IsFunctionDeclared;
import org.example.resolution_validators.IsOperationValid;
import org.example.resolution_validators.IsSimpleDeclaration;
import org.example.resolution_validators.IsResolutionSuccessful;

import java.util.List;
import java.util.Optional;

public class EvaluableVisitor implements Visitor<EvaluableResolution> {
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
    public EvaluableResolution visit(BinaryExpression expression) {
        var leftResolution = expression.getLeftComponent().accept(this);
        var rightResolution = expression.getRightComponent().accept(this);

        return new IsResolutionSuccessful(
                leftResolution,
                new IsResolutionSuccessful(
                        rightResolution,
                            new IsOperationValid(
                                    lang,
                                    leftResolution,
                                    rightResolution,
                                    expression,
                                    (env) -> new EvaluableResolution(
                                            new SemanticSuccess(),
                                            Optional.of(lang.getResolvedType(
                                                    leftResolution.evaluatedType().get(),
                                                    expression.getOperator(),
                                                    rightResolution.evaluatedType().get())
                                            ),
                                            true,
                                            Optional.empty()
                                    ),
                                    (env) -> EvaluableResolution.emptyFailure(
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
                    throw new RuntimeException("Invalid operator: " + operator + "(EvaluableResolution return for this type yet to be implemented)");
        };
    }

    @Override
    public EvaluableResolution visit(Conditional conditional) {
        return null;
    }

    @Override
    public EvaluableResolution visit(IfStatement ifStatement) {
        return null;
    }

    @Override
    public EvaluableResolution visit(Literal<?> literal) {
        return new EvaluableResolution(
                new SemanticSuccess(),
                Optional.ofNullable(mapToDeclarationType(literal)),
                literal.getValue() != null,
                Optional.empty()
        );
    }

    private DeclarationType mapToDeclarationType(Literal<?> literal) {
        return switch(literal.getValue()) {
            case String ignoredString -> DeclarationType.STRING;
            case Number ignoredNumber -> DeclarationType.NUMBER;
            case null -> null;
            default -> throw new IllegalStateException("Unexpected value: " + literal.getValue());
        };
    }

    @Override
    public EvaluableResolution visit(Parameters parameters) {
        return null;
    }

    @Override
    public EvaluableResolution visit(AssignationStatement statement) {
        IdentifierResolution leftResolution = statement.getLeft().accept(identifierVisitor);
        EvaluableResolution rightResolution = statement.getRight().accept(this);

        return new IsResolutionSuccessful(
                rightResolution,
                new IsDeclarationElseAssignation(
                        leftResolution,
                        new IdentifierExists(
                                leftResolution,
                                (env) -> EvaluableResolution.emptyFailure("Variable has already been declared"),
                                new IsSimpleDeclaration(
                                        rightResolution,
                                        (env) -> {
                                            env.declareVariable(leftResolution.name(), leftResolution.type().get());
                                            return EvaluableResolution.emptySuccess();
                                        },
                                        new AssigningToValidValue(
                                                leftResolution,
                                                rightResolution,
                                                (env) -> {
                                                    env.declareVariable(leftResolution.name(), leftResolution.type().get());
                                                    return EvaluableResolution.emptySuccess();
                                                },
                                                (env) -> EvaluableResolution.emptyFailure(
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
                                        (env) -> EvaluableResolution.emptySuccess(),
                                        (env) -> EvaluableResolution.emptyFailure(
                                                "Cannot assign type " + rightResolution.evaluatedType().get()
                                                        + " to " + env.getDeclarationType(leftResolution.name())
                                        )
                                ),
                                (env) -> EvaluableResolution.emptyFailure("Cannot assign non-existing identifier")
                        )
                ),
                (env) -> rightResolution
        ).analyze(env);
    }

    @Override
    public EvaluableResolution visit(Declaration statement) {
        return null;
    }

    @Override
    public EvaluableResolution visit(FunctionCallStatement statement) {
        IdentifierResolution functionCallResolution = statement.getLeft().accept(identifierVisitor);
        ParametersResolution parameterResolution = statement.getRight().accept(parametersVisitor);
        List<DeclarationType> types = parameterResolution.types();
        String functionName = functionCallResolution.name();

        return new IsResolutionSuccessful(
                functionCallResolution,
                new IsResolutionSuccessful(
                        parameterResolution,
                        new IsFunctionDeclared(
                                parameterResolution,
                                functionCallResolution,
                                (env) -> EvaluableResolution.emptySuccess(),
                                (env) -> EvaluableResolution.emptyFailure("Cannot resolve " + functionName + "(" + types + ").")
                        ),
                        (env) -> EvaluableResolution.emptyFailure(parameterResolution.result().errorMessage())
                ),
                (env) -> EvaluableResolution.emptyFailure(functionCallResolution.result().errorMessage())
        ).analyze(env);
    }

    @Override
    public EvaluableResolution visit(StatementBlock statementBlock) {
        return null;
    }

    @Override
    public EvaluableResolution visit(Identifier identifier) {
        return new IdentifierExists(
                identifier,
                (env) -> new EvaluableResolution(
                        new SemanticSuccess(),
                        Optional.of(env.getDeclarationType(identifier.getName())),
                        false,
                        Optional.of(identifier.getName())
                ),
                (env) -> new EvaluableResolution(
                        new SemanticFailure("Cannot find identifier " + identifier.getName()),
                        Optional.empty(),
                        false,
                        Optional.of(identifier.getName())
                )
        ).analyze(env);
    }
}
