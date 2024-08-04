package semantic_analyzer;

import model.AstComponent;
import model.DeclarationType;

import java.util.Map;

public record Resolution(
        SemanticResult result,
        AstComponent resolvedAst
) {
    public static Resolution failure(String reason) {
        return new Resolution(
                new SemanticFailure(reason),
                null
        );
    }
}
