package lexer.scanresult;

public class FailedScanResult implements ScanResult {
    private final int position;
    private final String message;

    public FailedScanResult(int position, String message) {
        this.position = position;
        this.message = message;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    public int getPosition() {
        return position;
    }

    public String getMessage() {
        return message;
    }
}
