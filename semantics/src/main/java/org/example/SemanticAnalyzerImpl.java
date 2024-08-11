package org.example;

import org.example.ast.AstComponent;
import org.example.ast.BinaryExpression;
import org.example.ast.Conditional;
import org.example.ast.Declaration;
import org.example.ast.Literal;
import org.example.ast.Parameters;
import org.example.ast.StatementBlock;
import org.example.ast.VariableIdentifier;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.resolution_validators.AssigningToValidValue;
import org.example.resolution_validators.IdentifierExists;
import org.example.resolution_validators.IsDeclarationElseReassignation;
import org.example.resolution_validators.IsSimpleDeclaration;
import org.example.resolution_validators.IsSuccessful;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SemanticAnalyzerImpl implements SemanticAnalyzer {
    // TODO: may define externally, such as in a config file
    private final Environment baseEnvironment;

    public SemanticAnalyzerImpl(Environment baseEnvironment) {
        this.baseEnvironment = baseEnvironment;
    }

    @Override
    public SemanticResult analyze(List<AstComponent> asts) {
        Environment env = baseEnvironment.copy();
        
        for (AstComponent ast : asts) {
            var resolution = ast.accept(this);
            if (!resolution.result().isSuccessful()) return resolution.result();
        }

        return new SemanticSuccess();
    }

    @Override
    public Resolution visit(BinaryExpression expression) {
        return null;
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
        return null;
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
                leftResolution,
                new IsSuccessful(
                        rightResolution,
                        new IsDeclarationElseReassignation(
                                leftResolution,
                                new IdentifierExists(
                                        leftResolution,
                                        (env) -> Resolution.failure("Variable has already been declared"),
                                        new IsSimpleDeclaration(
                                                rightResolution,
                                                (env) -> Resolution.success(leftResolution.resolvedDeclarations()),
                                                new AssigningToValidValue(
                                                        leftResolution,
                                                        rightResolution,
                                                        (env) -> Resolution.success(leftResolution.resolvedDeclarations()),
                                                        (env) -> Resolution.failure(
                                                                "Cannot assign type " + rightResolution.evaluatedType().get()
                                                                        + " to " + leftResolution.evaluatedType().get()
                                                        )
                                                )

                                        )
                                ),
                                new IdentifierExists(
                                        rightResolution,
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
                ),
                (env) -> leftResolution
        ).analyze(baseEnvironment);
    }

    @Override
    public Resolution visit(Declaration statement) {
        return null;
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
        if (!baseEnvironment.isVariableDeclared(variableIdentifier.getName())) {
            return Resolution.failure("Cannot find identifier " + variableIdentifier.getName());
        }
        else {
            return new Resolution(
                    new SemanticSuccess(),
                    Optional.of(baseEnvironment.getDeclarationType(variableIdentifier.getName())),
                    false,
                    Optional.of(variableIdentifier.getName()),
                    Collections.emptySet()
            );
        }
    }
}
