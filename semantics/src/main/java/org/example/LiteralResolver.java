package org.example;

import java.util.Map;

public class LiteralResolver implements Resolver {
    @Override
    public Resolution resolve(AstComponent ast, Environment env, Map<Class<? extends AstComponent>, Resolver> resolvers) {
        // Do nothing
        return new Resolution(
                new SemanticSuccess(),
                ast
        );
    }
}
