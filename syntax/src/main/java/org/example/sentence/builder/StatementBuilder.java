package org.example.sentence.builder;

import org.example.Pair;
import org.example.ast.statement.Statement;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.SentenceValidator;
import org.example.token.Token;

import java.util.List;

public abstract class StatementBuilder {
	protected final SentenceValidator validator;
	protected final TokenMapper mapper = new TokenMapper();

	public StatementBuilder(SentenceValidator validator) {
		this.validator = validator;
	}

	public StatementBuilder() {
		validator = null;
	}

	public abstract Pair<Statement, String> buildStatement(List<Token> tokens);
}
