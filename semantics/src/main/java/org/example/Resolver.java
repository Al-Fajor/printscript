package org.example;

import org.example.ast.AstComponent;

import java.util.Map;

public interface Resolver {
    Resolution resolve(
            AstComponent ast,
            Environment env,
            // TODO: could replace this parameter by a ResolverProvider, passed by constructor.
            //  this would also encapsulate the get().resolve() behaviour
            Map<Class<? extends AstComponent>, Resolver> resolvers
    );
}
