package org.example.sentence.validator;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;
import org.example.token.TokenType;

public class FunctionSentenceValidator implements SentenceValidator {
	@Override
	public boolean isValidSentence(List<Token> tokens) {
		return checkValidity(tokens);
	}

	private boolean checkValidity(List<Token> tokens) {
		// FUNCTION | PRINTLN -> SEPARATOR("(") -> ANYTHING -> SEPARATOR(")") -> ANYTHING ->
		// SEMICOLON
		CommonValidator validator = new CommonValidator();
		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			TokenType tokenType = token.getType();
			Token nextToken = i + 1 >= tokens.size() ? null : tokens.get(i + 1);
      //Hardcoded SEPARATOR case, may need optimization
      if(token.getType() == SEPARATOR){
        if(!(validator.areParenthesesBalanced(tokens) && validator.isValidToken(token, nextToken))){
          return false;
        }
        else continue;
      }
			if (validator.isNotSpecialToken(token)) {
				if (!validator.isValidToken(token, nextToken)) return false;
			}
			if (isNotValidSequence(tokenType, nextToken)) return false;
		}
		return true;
	}

	private boolean isNotValidSequence(TokenType tokenType, Token nextToken) {
		TokenMapper mapper = new TokenMapper();
		switch (tokenType) {
			case PRINTLN, FUNCTION -> {
				if (nextToken == null) return true;
				if (!mapper.matchesSeparatorType(nextToken, "opening")) return true;
			}
			case LITERAL, IDENTIFIER -> {
				if (nextToken == null) return true;
				if (!List.of(OPERATOR, SEMICOLON, SEPARATOR).contains(nextToken.getType())
						&& !mapper.matchesSeparatorType(nextToken, "closing")) return true;
			}
			default -> {
				return false;
			}
		}
		return false;
	}
}
