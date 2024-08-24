package org.example.utils;

import java.util.Optional;
import java.util.function.BiFunction;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DoubleOptional<T, S> {
	private final Optional<T> optional1;
	private final Optional<S> optional2;

	public static <T, S> DoubleOptional<T, S> from(Optional<T> optional1, Optional<S> optional2) {
		return new DoubleOptional<>(optional1, optional2);
	}

	private DoubleOptional(Optional<T> optional1, Optional<S> optional2) {
		this.optional1 = optional1;
		this.optional2 = optional2;
	}

    /** Returns Optional.empty() if any of the two optionals is empty */
	public <U> Optional<U> map(BiFunction<? super T, ? super S, ? extends U> mapper) {
		return optional1.flatMap(arg1 -> optional2.map(arg2 -> mapper.apply(arg1, arg2)));
	}
}
