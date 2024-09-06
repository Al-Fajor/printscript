package org.example.evaluables;

import java.util.Arrays;
import java.util.Optional;
import org.example.Pair;
import org.example.Resolution;
import org.example.Result;
import org.example.SemanticFailure;
import org.example.SemanticSuccess;
import org.example.ast.DeclarationType;

public record EvaluableResolution(
		Result result, Optional<DeclarationType> evaluatedType, Optional<String> identifierName)
		implements Resolution {

	public static final String BAD_USAGE_ERROR_MESSAGE =
			"EvaluableResolution.failure(Resolution) must be called with a failed Resolution";

	public static EvaluableResolution failure(
			String reason, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {

		return new EvaluableResolution(
				new SemanticFailure(reason, Optional.of(start), Optional.of(end)),
				Optional.empty(),
				Optional.empty());
	}

	public static EvaluableResolution castFrom(Resolution otherResolution) {
		// If I extract the throwable, the compiler does not know it's an unchecked exception
		// and starts crying over it.
		return EvaluableResolution.failure(
				otherResolution.result().errorMessage(),
				otherResolution
						.result()
						.getErrorStart()
						.orElseThrow(() -> new IllegalArgumentException(BAD_USAGE_ERROR_MESSAGE)),
				otherResolution
						.result()
						.getErrorEnd()
						.orElseThrow(() -> new IllegalArgumentException(BAD_USAGE_ERROR_MESSAGE)));
	}

	public static EvaluableResolution emptySuccess() {
		return new EvaluableResolution(new SemanticSuccess(), Optional.empty(), Optional.empty());
	}

	public static Optional<EvaluableResolution> returnFirstFailedResolution(
			EvaluableResolution... resolutions) {
		return Arrays.stream(resolutions).filter(resolution -> resolution.failed()).findFirst();
	}
}
