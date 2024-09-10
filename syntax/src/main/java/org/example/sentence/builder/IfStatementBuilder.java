package org.example.sentence.builder;

import org.example.Pair;
import org.example.ast.Identifier;
import org.example.ast.statement.IfElseStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.statement.Statement;
import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.sentence.builder.SentenceBuilder.errorPair;
import static org.example.token.BaseTokenTypes.*;

public class IfStatementBuilder {
	private final TokenMapper mapper = new TokenMapper();

	public Pair<Statement, String> buildSentence(List<Token> tokens) {
		if (!tokens.get(1).getValue().equals("(")) {
			return errorPair("Expected '(' after 'if'");
		}

		int condEndIndex = findMatchingParenthesis(tokens, 1);
		if (condEndIndex == -1) {
			return errorPair("Unbalanced parentheses in 'if' condition");
		}
		List<Token> conditionTokens = tokens.subList(2, condEndIndex);

		if (conditionTokens.getFirst().getType() != IDENTIFIER || conditionTokens.size() > 1) {
			return errorPair("Incorrect condition");
		}

		if (!tokens.get(condEndIndex + 1).getValue().equals("{")) {
			return errorPair("Expected '{' after 'if' condition");
		}

		int ifBlockStart = condEndIndex + 2;
		int ifBlockEnd = findMatchingBrace(tokens, ifBlockStart - 1);
		if (ifBlockEnd == -1) {
			return errorPair("Unbalanced braces in 'if' block");
		}
		Iterable<Statement> ifBlock =
				buildSentenceIterable(tokens.subList(ifBlockStart, ifBlockEnd));

		boolean hasElseCondition =
				tokens.size() > ifBlockEnd + 1 && tokens.get(ifBlockEnd).getType() == ELSE;
		if (hasElseCondition) {

			if (!tokens.get(ifBlockEnd + 1).getValue().equals("{")) {
				return errorPair("Expected '{' after 'else'");
			}

			int elseBlockStart = ifBlockEnd + 2;
			int elseBlockEnd = findMatchingBrace(tokens, elseBlockStart - 1);
			if (elseBlockEnd == -1) {
				return errorPair("Unbalanced braces in 'else' block");
			}
			Iterable<Statement> elseBlock =
					buildSentenceIterable(tokens.subList(elseBlockStart, elseBlockEnd));

			Statement ifElseStatement =
					new IfElseStatement(
							(Identifier) mapper.mapToken(tokens.get(2)),
							ifBlock,
							elseBlock,
							tokens.getFirst().getStart(),
							tokens.getLast().getEnd());
			return new Pair<>(ifElseStatement, "Not an error");
		}

		Statement ifStatement =
				new IfStatement(
						(Identifier) mapper.mapToken(tokens.get(2)),
						ifBlock,
						tokens.getFirst().getStart(),
						tokens.getLast().getEnd());
		return new Pair<>(ifStatement, "Not an error");
	}

	private int findMatchingParenthesis(List<Token> tokens, int startIndex) {
		int parenCount = 0;
		for (int i = startIndex; i < tokens.size(); i++) {
			if (tokens.get(i).getValue().equals("(")) {
				parenCount++;
			} else if (tokens.get(i).getValue().equals(")")) {
				parenCount--;
				if (parenCount == 0) {
					return i;
				}
			}
		}
		return -1;
	}

	private Iterable<Statement> buildSentenceIterable(List<Token> tokens) {
		SentenceBuilder builder = new SentenceBuilder();
		List<List<Token>> sentences = splitTokens(tokens);
		List<Pair<Optional<Statement>, String>> statements =
				sentences.stream().map(builder::buildSentence).toList();
		return statements.stream()
				.map(Pair::first)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.toList();
	}

	private List<List<Token>> splitTokens(List<Token> tokens) {
		List<List<Token>> sentences = new ArrayList<>();
		List<Token> currentSentence = new ArrayList<>();
		int braceCount = 0;

		for (Token token : tokens) {
			if (token.getType() == SEPARATOR) {
				if (token.getValue().equals("{")) {
					braceCount++;
				} else if (token.getValue().equals("}")) {
					braceCount--;
				}
			}

			currentSentence.add(token);

			if ((token.getType() == SEMICOLON
							|| (token.getType() == SEPARATOR && token.getValue().equals("}")))
					&& braceCount == 0) {
				sentences.add(new ArrayList<>(currentSentence));
				currentSentence.clear();
			}
		}

		if (!currentSentence.isEmpty()) {
			sentences.add(currentSentence);
		}

		return sentences;
	}

	private int findMatchingBrace(List<Token> tokens, int startIndex) {
		int braceCount = 0;
		for (int i = startIndex; i < tokens.size(); i++) {
			if (mapper.matchesSeparatorType(tokens.get(i), "opening brace")) {
				braceCount++;
			} else if (mapper.matchesSeparatorType(tokens.get(i), "closing brace")) {
				braceCount--;
				if (braceCount == 0) {
					return i + 1;
				}
			}
		}
		return -1;
	}
}
