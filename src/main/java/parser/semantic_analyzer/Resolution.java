package parser.semantic_analyzer;

import model.AstComponent;

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
