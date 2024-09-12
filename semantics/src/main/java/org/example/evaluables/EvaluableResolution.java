package org.example.evaluables;

import java.util.Optional;
import org.example.Pair;
import org.example.Resolution;
import org.example.ResolvedType;
import org.example.Result;
import org.example.SemanticFailure;
import org.example.SemanticSuccess;
import org.example.ast.IdentifierType;
import org.example.utils.TripleOptional;

public record EvaluableResolution(
		Result result,
		Optional<ResolvedType> evaluatedType,
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

	public TripleOptional<ResolvedType, IdentifierType, String> asTripleOptional() {
		return TripleOptional.from(evaluatedType, identifierType, identifierName);
	}

	public static EvaluableResolution emptySuccess() {
		return new EvaluableResolution(
				new SemanticSuccess(), Optional.empty(), Optional.empty(), Optional.empty());
	}
}
