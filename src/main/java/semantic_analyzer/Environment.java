package semantic_analyzer;

import model.DeclarationType;
import model.Identifier;

import java.util.List;

public interface Environment {
    boolean isVariableDeclared(String name);
    boolean isFunctionDeclared(String name, List<DeclarationType> parameters);
    DeclarationType getDeclarationType(String name);
    void declareVariable(String name, DeclarationType type);
    void declareFunction(String name, DeclarationType... parameters);
    Environment copy();
}
