package org.example;

import org.example.ast.AstComponent;
import org.example.evaluables.EvaluableVisitor;
import org.example.identifiers.IdentifierVisitor;
import org.example.parameters.ParametersVisitor;

import java.util.List;

public class SemanticAnalyzerImpl implements SemanticAnalyzer {
    // TODO: may define externally, such as in a config file
    private final Environment baseEnvironment;
    private final ParametersVisitor parametersVisitor = new ParametersVisitor();
    private final IdentifierVisitor identifierVisitor = new IdentifierVisitor();
    private final EvaluableVisitor evaluableVisitor = new EvaluableVisitor(null, identifierVisitor, parametersVisitor);

    public SemanticAnalyzerImpl(Environment baseEnvironment) {
        this.baseEnvironment = baseEnvironment;
        parametersVisitor.setEvaluableVisitor(evaluableVisitor);
    }

    @Override
    public SemanticResult analyze(List<AstComponent> asts) {
        Environment env = baseEnvironment.copy();
        evaluableVisitor.setEnv(env);
        
        for (AstComponent ast : asts) {
            var resolution = ast.accept(evaluableVisitor);
            if (!resolution.result().isSuccessful()) return resolution.result();
        }

        return new SemanticSuccess();
    }
}
