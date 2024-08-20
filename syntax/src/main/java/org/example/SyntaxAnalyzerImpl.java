package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.ast.AstComponent;
import org.example.observer.Observer;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;
import org.example.result.SyntaxSuccess;
import org.example.sentence.builder.SentenceBuilder;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;

public class SyntaxAnalyzerImpl implements SyntaxAnalyzer {
	List<Observer<Pair<Integer, Integer>>> observers = new ArrayList<>();

	@Override
	public SyntaxResult analyze(List<Token> tokens) {
		if (tokens.isEmpty()) return new SyntaxSuccess(List.of());
		return buildSentences(tokens);
	}

	private SyntaxResult buildSentences(List<Token> tokens) {
		List<List<Token>> sentences =
				getSentencesWithTokens(tokens); // Separate sentences by semicolons
		SyntaxResult result =
				getSyntaxResult(sentences); // Get result (if there's an error, returns error)

		System.out.println(result.errorMessage()); // Print it

		return result; // Return it
	}

	private SyntaxResult getSyntaxResult(List<List<Token>> tokenSentences) {
		List<AstComponent> finalComponents = new ArrayList<>();

		for (int i = 0; i < tokenSentences.size(); i++) {

			List<Token> currentSentence = tokenSentences.get(i);
			Optional<AstComponent> component = buildSentence(currentSentence);

			final int completed = i;
			observers.forEach(
					observer ->
							observer.notifyChange(
									new Pair<>(
											completed,
											tokenSentences
													.size()))); // TODO: may need a line change

			if (component.isPresent()) {
				finalComponents.add(component.get());

			} else {
				return new SyntaxError(
						new Pair<>(i, 0),
						new Pair<>(i, currentSentence.size() - 1),
						parseError(currentSentence));
			}
		}
		return new SyntaxSuccess(finalComponents);
	}

	private Optional<AstComponent> buildSentence(List<Token> sentence) {
		SentenceBuilder builder = new SentenceBuilder();
		AstComponent component = builder.buildSentence(sentence);
		return component != null ? Optional.of(component) : Optional.empty();
	}

	private List<List<Token>> getSentencesWithTokens(List<Token> tokens) {
		List<List<Token>> sentences = new ArrayList<>();
		int i = 0;
		for (int j = 0; j < tokens.size(); j++) {
			if (tokens.get(j).getType() == BaseTokenTypes.SEMICOLON) {
				sentences.add(tokens.subList(i, j + 1));
				i = j + 1;
				if (i >= tokens.size()) break;
			}
		}
		return sentences;
	}

	@Override
	public void addObserver(Observer<Pair<Integer, Integer>> observer) {
		observers.add(observer);
	}

	private String parseError(List<Token> currentSentence) {
		// TODO: parse error message correctly
		return "Invalid syntax: " + currentSentence.getFirst().toString();
	}
}
