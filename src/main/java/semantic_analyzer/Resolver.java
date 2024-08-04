package semantic_analyzer;

import model.AstComponent;
import model.Declaration;
import model.DeclarationType;

import java.util.Map;
import java.util.Set;

public interface Resolver {
    Resolution resolve(
            AstComponent ast,
            Map<String, DeclarationType> previousDeclarations previousDeclarations,
            Map<Class<? extends AstComponent>, Resolver> resolvers
    );
}
