package parser.semantic_analyzer;

import model.AstComponent;
import model.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParametersResolver implements Resolver {
    @Override
    public Resolution resolve(
            AstComponent ast,
            Environment env, Map<Class<? extends AstComponent>,
            Resolver> resolvers
    ) {
        var parameters = (Parameters) ast;

        List<AstComponent> resolvedParameters = new ArrayList<>();

        for (AstComponent astComponent: parameters.getParameters()) {
            Resolution astResolution = resolvers
                    .get(astComponent.getClass())
                    .resolve(astComponent, env, resolvers);

            if (astResolution.result().isFailure()) {
                return astResolution;
            }
            else {
                resolvedParameters.add(astResolution.resolvedAst());
            }
        }

        return new Resolution(
                new SemanticSuccess(),
                new Parameters(resolvedParameters)
        );
    }
}
