package org.example;

public class SemanticSuccess implements SemanticResult {
    @Override
    public boolean isFailure() {
        return false;
    }
}
