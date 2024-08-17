package org.example.lexerresult;

import java.util.List;
import org.example.scanresult.FailedScanResult;
import org.example.token.Token;

public class LexerFailure implements LexerResult {
	private final FailedScanResult result;

	public LexerFailure(FailedScanResult result) {
		this.result = result;
	}

	@Override
	public List<Token> getTokens() {
		return List.of();
	}

	@Override
	public boolean isSuccessful() {
		return false;
	}

	public String message() {
		return result.getMessage();
	}

	public int position() {
		return result.getPosition();
	}
}
