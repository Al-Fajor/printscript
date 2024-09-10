package org.example;

import org.example.ast.statement.Statement;
import org.example.observer.Observer;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;
import org.example.result.SyntaxSuccess;
import org.example.sentence.builder.SentenceBuilder;
import org.example.token.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.example.token.BaseTokenTypes.*;

public class SyntaxAnalyzerImpl implements SyntaxAnalyzer {
	List<Observer<Pair<Integer, Integer>>> observers = new ArrayList<>();
	private static final Integer LIMIT = 100;

	@Override
	public SyntaxResult analyze(Iterator<Token> tokens) {
		if (!tokens.hasNext()) return new SyntaxError(null, null, "No tokens provided");
		return buildSentences(tokens);
	}

	private SyntaxResult buildSentences(Iterator<Token> tokens) {
		List<Token> statementTokens =
				getStatementTokens(
						tokens); // Separate sentences by semicolons, or by braces whenever
		// encountering ifs

		return getSyntaxResult(statementTokens); // Return it
	}

	private SyntaxResult getSyntaxResult(List<Token> tokens) {
		SentenceBuilder builder = new SentenceBuilder();
		Pair<Optional<Statement>, String> sentenceOptional = builder.buildSentence(tokens);

		for (int i = 1; i < LIMIT; i++) {
			final int completed = i;
			observers.forEach(observer -> observer.notifyChange(new Pair<>(completed + 1, LIMIT)));
		}

		if (sentenceOptional.first().isEmpty()) {
			return new SyntaxError(
					tokens.getFirst().getStart(),
					tokens.getLast().getEnd(),
					sentenceOptional.second());
		}

		return new SyntaxSuccess(sentenceOptional.first().get());
	}

	private List<Token> getStatementTokens(Iterator<Token> tokens) {
		List<Token> sentences = new ArrayList<>();
		Token current = tokens.next();
		sentences.add(current);

		if (current.getType() == IF || current.getType() == ELSE) {
			int braceCount =
					0; // start with 0 because we don't even know if the sentence is correctly
			// written
			while (tokens.hasNext()) {
				Token toAdd = tokens.next();
				sentences.add(toAdd);
				if (toAdd.getType() == SEPARATOR) {
					if (toAdd.getValue().equals("{")) {
						braceCount++;
					} else if (toAdd.getValue().equals("}")) {
						braceCount--;
						if (braceCount == 0) {
							if (tokens.hasNext()) continue;
							break;
						}
					}
				}
			}
		} else {
			while (current.getType() != SEMICOLON && tokens.hasNext()) {
				Token toAdd = tokens.next();
				sentences.add(toAdd);
				current = toAdd;
			}
		}

		return sentences;
	}

	@Override
	public void addObserver(Observer<Pair<Integer, Integer>> observer) {
		observers.add(observer);
	}
}
