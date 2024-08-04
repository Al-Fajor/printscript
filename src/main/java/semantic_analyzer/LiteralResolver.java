package semantic_analyzer;

import model.AstComponent;
import model.DeclarationType;
import model.Literal;

import java.util.Collections;
import java.util.Map;

public class LiteralResolver implements Resolver {
    @Override
    public Resolution resolve(AstComponent ast, Map<String, DeclarationType> previousDeclarations, Map<Class<? extends AstComponent>, Resolver> resolvers) {
        // Do nothing
        return new Resolution(
                new SemanticSuccess(),
                ast,
                Collections.emptyMap()
        );
    }
}
