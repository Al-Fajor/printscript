package org.example.token;

public class Token {
    private final TokenType type;
    private final Integer start;
    private final Integer end;
    private final String value;

    public Token(TokenType type, Integer start, Integer end, String value) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", start=" + start +
                ", end=" + end +
                ", value='" + value + '\'' +
                '}';
    }
}
