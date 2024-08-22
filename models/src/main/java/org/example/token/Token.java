package org.example.token;

import org.example.Pair;

public class Token {
	private final TokenType type;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;
	private final String value;

	public Token(
			TokenType type,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end,
			String value) {
		this.type = type;
		this.start = start;
		this.end = end;
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public Pair<Integer, Integer> getStart() {
		return start;
	}

	public Pair<Integer, Integer> getEnd() {
		return end;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Token{"
				+ "type="
				+ type
				+ ", start="
				+ start
				+ ", end="
				+ end
				+ ", value='"
				+ value
				+ '\''
				+ '}';
	}
}
