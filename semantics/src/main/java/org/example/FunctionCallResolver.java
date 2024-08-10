package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionCallResolver implements Resolver {
    @Override
    public Resolution resolve(
            AstComponent ast,
            Environment env,
            Map<Class<? extends AstComponent>, Resolver> resolvers
    ) {
        var functionCall = (FunctionCall) ast;

        Resolution parameterResolution =
                resolvers
                        .get(Parameters.class)
                        .resolve(functionCall.getParameters(), env, resolvers);

        if (parameterResolution.result().isFailure()) return parameterResolution;



        var resolvedParametersAst = ((Parameters) parameterResolution.resolvedAst());

        List<DeclarationType> parameters = new ArrayList<>();

        for (AstComponent parameter: resolvedParametersAst.getParameters()) {
            var literal = (Literal<?>) parameter;
            parameters.add(getDeclarationType(literal));
        }

        String functionName = functionCall.getIdentifier().getName();
        if (!env.isFunctionDeclared(functionName, parameters)) {
            return Resolution.failure("Cannot resolve " + functionName + "(" + parameters + ").");
        }
        return new Resolution(
                new SemanticSuccess(),
                // TODO: resolve into Literal if not void
                functionCall
        );
    }

    // TODO: Strategyze this and use it in getErrorOnTypeMismatch
    private DeclarationType getDeclarationType(Literal<?> literal) {
        if (literal.getValue() instanceof String) {
            return DeclarationType.STRING;
        }
        else if (literal.getValue() instanceof Number) {
            return DeclarationType.NUMBER;
        }
        else throw new IllegalStateException("Resolved into invalid declaration type: " + literal.getValue());
    }
}
