package org.example;

public record SemanticFailure(String errorMessage) implements SemanticResult {

    @Override
    public boolean isSuccessful() {
        return false;
    }
}
