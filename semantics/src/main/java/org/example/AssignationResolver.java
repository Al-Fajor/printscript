package org.example;

import model.Assignation;
import model.AstComponent;
import model.Declaration;
import model.DeclarationType;
import model.Identifier;
import model.Literal;

import java.util.Map;

public class AssignationResolver implements Resolver {
    //TODO: modularize
    //TODO: maybe some things could be moved to an IdentifierResolver and a DeclarationResolver
    //TODO: identifiers may not be variables, but functions, so this should be checked
    @Override
    public Resolution resolve(
            AstComponent ast,
            Environment env,
            Map<Class<? extends AstComponent>, Resolver> resolvers
    ) {
        // TODO: should actually avoid casting, but idk how to pass the AST
        //  with its real type
        var assignation = (Assignation) ast;
        AstComponent right = assignation.getRightComponent();
        AstComponent left = assignation.getLeftComponent();

        System.out.println(right.getClass());
        var rightResolution =
                resolvers
                .get(right.getClass())
                .resolve(right, env, resolvers);

        if (rightResolution.result().isFailure()) return rightResolution;
        if (!(rightResolution.resolvedAst() instanceof Literal<?> literal)) {
            return Resolution.failure("Cannot assign variable: need an expression which resolves into a literal");
        }

        if (left instanceof Declaration declaration) {
            String identifierName = declaration.getName();
            if (env.isVariableDeclared(identifierName)) {
                return Resolution.failure(identifierName + " is already declared");
            }

            String error = getErrorOnTypeMismatch(declaration.getType(), literal);
            if (!error.isEmpty()) {
                return Resolution.failure(error);
            }

            env.declareVariable(declaration.getName(), declaration.getType());
            return new Resolution(new SemanticSuccess(), assignation);
        }

        else if (left instanceof Identifier identifier) {
            String identifierName = identifier.getName();
            if (!env.isVariableDeclared(identifierName)) {
                return Resolution.failure( "Cannot reassign " + identifierName + " because it has not been declared");
            }

            var declarationType = env.getDeclarationType(identifierName);
            String error = getErrorOnTypeMismatch(declarationType, literal);
            if (!error.isEmpty()) {
                return Resolution.failure(error);
            }

            return new Resolution(new SemanticSuccess(), assignation);
        }

        else {
            // Should be unreachable
            throw new IllegalArgumentException("Received Assignation with syntax error: there is neither an Identifier nor a Declaration on the left");
        }
    }

    private String getErrorOnTypeMismatch(DeclarationType declarationType, Literal<?> literal) {
        boolean notString =
                declarationType == DeclarationType.STRING
                && !(literal.getValue() instanceof String);

        boolean notNumber =
                declarationType == DeclarationType.NUMBER
                && !(literal.getValue() instanceof Number);

        if (notString || notNumber) {
            return "Cannot assign " + literal.getValue() + " to type " + declarationType;
        }

        return "";
    }
}
