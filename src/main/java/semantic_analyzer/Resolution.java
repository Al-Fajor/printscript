package semantic_analyzer;

import model.AstComponent;
import model.Declaration;
import model.DeclarationType;

import java.util.Map;
import java.util.Set;

public record Resolution(
        SemanticResult result,
        AstComponent resolvedAst,
        Map<String, DeclarationType> resolvedDeclarations
) {
    public static Resolution failure(String reason) {
        return new Resolution(
                new SemanticFailure(reason),
                null,
                null
        );
    }
}
