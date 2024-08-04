package semantic_analyzer;

import model.AstComponent;
import model.DeclarationType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzerImpl implements SemanticAnalyzer {
    // TODO: may wrap this horrible map within a helper class
    private Map<Class<? extends AstComponent>, Resolver> resolvers;

    public AnalyzerImpl(Map<Class<? extends AstComponent>, Resolver> resolvers) {
        this.resolvers = resolvers;
    }

    @Override
    public SemanticResult analyze(List<AstComponent> asts) {
        Map<String, DeclarationType> declarations = new HashMap<>();

        for (AstComponent ast : asts) {
            var resolution = resolvers.get(ast.getClass()).resolve(ast, declarations, resolvers);
            if (resolution.result().isFailure()) return resolution.result();
            declarations.putAll(resolution.resolvedDeclarations());
        }

        return new SemanticSuccess();
    }
}
