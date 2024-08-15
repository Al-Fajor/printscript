package org.example.lexerresult;

import java.util.List;
import org.example.token.Token;

public interface LexerResult {
	List<Token> getTokens();

	boolean isSuccessful();
}
