package org.example.sentence.builder;

import org.example.Pair;
import org.example.ast.*;
import org.example.ast.statement.*;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.SentenceValidator;
import org.example.sentence.validator.validity.Validity;
import org.example.sentence.validator.validity.rule.RuleProvider;
import org.example.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.token.BaseTokenTypes.*;

public class SentenceBuilder {
	private final TokenMapper mapper = new TokenMapper();

	public Pair<Optional<Statement>, String> buildSentence(List<Token> tokens) {
		var sentence = getStatementPair(tokens);

		Optional<Statement> statement =
				sentence.first() == null ? Optional.empty() : Optional.of(sentence.first());

		return new Pair<>(statement, sentence.second());
	}

	private Pair<Statement, String> buildReassignationSentence(
			List<Token> tokens, SentenceValidator validator) {
		Validity validity = validator.getSentenceValidity(tokens);
		if (tokens.size() <= 2 || !validity.isValid()) return errorPair(validity.getErrorMessage());

		// TODO: eliminate casting
		Identifier identifier = (Identifier) mapper.mapToken(tokens.getFirst());

		EvaluableComponent value =
				mapper.buildExpression(tokens.subList(2, tokens.size())).getFirst();

		return new Pair<>(
				new AssignmentStatement(
						identifier, value, tokens.getFirst().getStart(), tokens.getLast().getEnd()),
				"Not an error");
	}

	private Pair<Statement, String> buildFunctionSentence(
			List<Token> tokens, SentenceValidator validator) {
		Token function = tokens.getFirst();
		Validity validity = validator.getSentenceValidity(tokens);
		if (!validity.isValid()) return errorPair(validity.getErrorMessage());

		List<EvaluableComponent> parameters =
				mapper.buildExpression(tokens.subList(1, tokens.size()));
		Pair<Integer, Integer> functionStart = function.getStart();
		Pair<Integer, Integer> functionEnd = function.getEnd();
		String name = function.getType() == PRINTLN ? "println" : function.getValue();

		Identifier id = new Identifier(name, functionStart, functionEnd);

		return new Pair<>(
				new FunctionCallStatement(
						id,
						new Parameters(
								parameters, tokens.get(1).getStart(), tokens.getLast().getEnd()),
						functionStart,
						tokens.getLast().getEnd()),
				validity.getErrorMessage());
	}

	private Pair<Statement, String> buildAssignationSentence(
			List<Token> tokens, SentenceValidator validator) {
		Validity validity = validator.getSentenceValidity(tokens);
		if (!validity.isValid()) return errorPair(validity.getErrorMessage());

		// May need to change method
		Token type = tokens.get(3);
		Token identifier = tokens.get(1);
		Token semicolon = tokens.getLast();

		Identifier identifierComponent = (Identifier) mapper.mapToken(identifier);

		DeclarationType declarationType = mapper.getDeclarationType(type.getValue());
		IdentifierType identifierType = mapper.getIdentifierType(tokens.getFirst());
		// let x: number;
		boolean isSemicolon = tokens.get(4).getType() == SEMICOLON;
		EvaluableComponent value =
				isSemicolon
						? new Literal<>(null, semicolon.getStart(), semicolon.getEnd())
						: mapper.buildExpression(tokens.subList(5, tokens.size())).getFirst();

		if (identifierType == IdentifierType.CONST && isSemicolon) {
			return errorPair("Cannot declare a CONST and not assign it");
		}

		Pair<Integer, Integer> start = tokens.getFirst().getStart();
		Pair<Integer, Integer> end = semicolon.getEnd();

		Statement declarationStatement =
				getDeclarationStatement(
						isSemicolon,
						declarationType,
						identifierType,
						identifierComponent,
						start,
						end,
						value);
		return new Pair<>(declarationStatement, validity.getErrorMessage());
	}

	private Pair<Statement, String> errorPair(String errorMessage) {
		return new Pair<>(null, errorMessage);
	}

	private Pair<Statement, String> buildConditionalSentence(List<Token> tokens) {
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
		List<List<Token>> sentences = splitTokens(tokens);
		List<Pair<Optional<Statement>, String>> statements =
				sentences.stream().map(this::buildSentence).toList();
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

	private Statement getDeclarationStatement(
			boolean isSemicolon,
			DeclarationType declarationType,
			IdentifierType identifierType,
			Identifier identifier,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end,
			EvaluableComponent value) {
		return isSemicolon
				? new DeclarationStatement(declarationType, identifierType, identifier, start, end)
				: new DeclarationAssignmentStatement(
						declarationType, identifierType, identifier, value, start, end);
	}

	private Pair<Statement, String> getStatementPair(List<Token> tokens) {
		Pair<Statement, String> errorPair =
				new Pair<>(
						null,
						"Invalid sentence. Should begin with PRINTLN, FUNCTION, IDENTIFIER, DECLARATION or IF");

		if (tokens == null) return errorPair;

		return switch (tokens.getFirst().getType()) {
			case LET, CONST -> buildAssignationSentence(tokens, getValidator(tokens));
			case FUNCTION, PRINTLN -> buildFunctionSentence(tokens, getValidator(tokens));
			case IDENTIFIER -> buildReassignationSentence(tokens, getValidator(tokens));
			case IF -> buildConditionalSentence(tokens);
			default -> errorPair;
		};
	}

	private SentenceValidator getValidator(List<Token> tokens) {
		return new SentenceValidator(
				new RuleProvider().getSentenceRules(tokens.getFirst().getType()));
	}
}
