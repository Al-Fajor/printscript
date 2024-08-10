package org.example;

import java.util.List;
import java.util.Map;

public class AnalyzerImpl implements SemanticAnalyzer {
    // TODO: may wrap this horrible map within a helper class
    private Map<Class<? extends AstComponent>, Resolver> resolvers;
    // TODO: may define externally, such as in a config file
    private final Environment baseEnvironment;

    public AnalyzerImpl(Map<Class<? extends AstComponent>, Resolver> resolvers, Environment baseEnvironment) {
        this.resolvers = resolvers;
        this.baseEnvironment = baseEnvironment;
    }

    @Override
    public SemanticResult analyze(List<AstComponent> asts) {
        Environment env = baseEnvironment.copy();
        
        for (AstComponent ast : asts) {
            var resolution = resolvers.get(ast.getClass()).resolve(ast, env, resolvers);
            if (resolution.result().isFailure()) return resolution.result();
        }

        return new SemanticSuccess();
    }
}
