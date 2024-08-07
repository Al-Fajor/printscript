package semantic_analyzer;

import model.AstComponent;
import model.DeclarationType;
import model.Identifier;
import model.Literal;

import java.util.Collections;
import java.util.Map;

import static semantic_analyzer.BinaryExpressionResolver.NUMBER_PLACEHOLDER;
import static semantic_analyzer.BinaryExpressionResolver.STRING_PLACEHOLDER;

public class IdentifierResolver implements Resolver {
    @Override
    public Resolution resolve(
            AstComponent ast,
            Environment env,
            Map<Class<? extends AstComponent>, Resolver> resolvers
    ) {
        var identifier = (Identifier) ast;

        if (!env.isVariableDeclared(identifier.getName())) {
            return Resolution.failure("Cannot find identifier " + identifier.getName());
        }
        else {
            return new Resolution(
                    new SemanticSuccess(),
                    getLiteralBasedOnType(identifier, env)
            );
        }
    }

    private Literal<?> getLiteralBasedOnType(Identifier identifier, Environment env) {
        DeclarationType type = env.getDeclarationType(identifier.getName());

        switch (type) {
            case NUMBER:
                return new Literal<>(NUMBER_PLACEHOLDER);
            case STRING:
                return new Literal<>(STRING_PLACEHOLDER);
            case FUNCTION:
                throw new RuntimeException("Yet to implement");
            default:
                // Unreachable
                throw new IllegalArgumentException("Invalid type");
        }
    }
}
