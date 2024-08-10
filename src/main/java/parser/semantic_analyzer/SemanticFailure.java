package parser.semantic_analyzer;

public class SemanticFailure implements SemanticResult {
    private final String reason;

    public SemanticFailure(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean isFailure() {
        return true;
    }
}
