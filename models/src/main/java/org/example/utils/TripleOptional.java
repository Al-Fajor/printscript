package org.example.utils;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class TripleOptional<T, U, V> {
	private final Optional<T> optional1;
	private final Optional<U> optional2;
	private final Optional<V> optional3;

	public static <T, U, V> TripleOptional<T, U, V> from(
			Optional<T> optional1, Optional<U> optional2, Optional<V> optional3) {
		return new TripleOptional<>(optional1, optional2, optional3);
	}

	private TripleOptional(Optional<T> optional1, Optional<U> optional2, Optional<V> optional3) {
		this.optional1 = optional1;
		this.optional2 = optional2;
		this.optional3 = optional3;
	}

	/** Returns Optional.empty() if any of the optionals is empty */
	public <R> Optional<R> map(TriFunction<? super T, ? super U, ? super V, ? extends R> mapper) {
		return optional1.flatMap(
				arg1 ->
						optional2.flatMap(
								arg2 -> optional3.map(arg3 -> mapper.apply(arg1, arg2, arg3))));
	}
}
