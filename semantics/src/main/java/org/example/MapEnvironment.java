package org.example;

import org.example.ast.DeclarationType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapEnvironment implements Environment {
    private final Map<String, DeclarationType> varDeclarations;
    private final Set<Signature> funDeclarations;

    public MapEnvironment(
            Map<String, DeclarationType> reservedVariables,
            Set<Signature> reservedFunctions
    ) {
        this.varDeclarations = reservedVariables;
        this.funDeclarations = reservedFunctions;
    }

    @Override
    public boolean isVariableDeclared(String name) {
        return varDeclarations.containsKey(name);
    }

    @Override
    public boolean isFunctionDeclared(String name, List<DeclarationType> parameters) {
        return funDeclarations.contains(new Signature(name, parameters));
    }

    @Override
    public DeclarationType getDeclarationType(String name) {
        return varDeclarations.get(name);
    }

    @Override
    public void declareVariable(String name, DeclarationType type) {
        varDeclarations.put(name, type);
    }

    @Override
    public void declareFunction(String name, DeclarationType... parameters) {
        funDeclarations.add(new Signature(name, List.of(parameters)));
    }

    @Override
    public Environment copy() {
        return new MapEnvironment(
                new HashMap<>(varDeclarations),
                new HashSet<>(funDeclarations)
        );
    }
}
