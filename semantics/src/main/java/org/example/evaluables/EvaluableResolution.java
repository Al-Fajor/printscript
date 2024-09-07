package org.example.evaluables;

import java.util.Arrays;
import java.util.Optional;
import org.example.Pair;
import org.example.Resolution;
import org.example.Result;
import org.example.SemanticFailure;
import org.example.SemanticSuccess;
import org.example.ast.DeclarationType;
import org.example.ast.IdentifierType;
import org.example.utils.TripleOptional;

public record EvaluableResolution(
		Result result,
		Optional<DeclarationType> evaluatedType,
		Optional<IdentifierType> identifierType,
		Optional<String> identifierName)
		implements Resolution {

	public static EvaluableResolution failure(
			String reason, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {

		return new EvaluableResolution(
				new SemanticFailure(reason, Optional.of(start), Optional.of(end)),
				Optional.empty(),
				Optional.empty(),
				Optional.empty());
	}

	public TripleOptional<DeclarationType, IdentifierType, String> asTripleOptional() {
		return TripleOptional.from(evaluatedType, identifierType, identifierName);
	}

	public static EvaluableResolution emptySuccess() {
		return new EvaluableResolution(
				new SemanticSuccess(), Optional.empty(), Optional.empty(), Optional.empty());
	}

	public static Optional<EvaluableResolution> returnFirstFailedResolution(
			EvaluableResolution... resolutions) {
		return Arrays.stream(resolutions).filter(resolution -> resolution.failed()).findFirst();
	}
}
