package semantic_analyzer;

import model.AstComponent;
import model.Declaration;
import model.DeclarationType;

import java.util.Map;
import java.util.Set;

public interface Resolver {
    Resolution resolve(
            AstComponent ast,
            Environment env,
            // TODO: could replace this parameter by a ResolverProvider, passed by constructor.
            //  this would also encapsulate the get().resolve() behaviour
            Map<Class<? extends AstComponent>, Resolver> resolvers
    );
}
