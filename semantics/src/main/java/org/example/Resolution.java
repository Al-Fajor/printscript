package org.example;

import java.util.Arrays;
import java.util.Optional;

public interface Resolution {
	Result result();

	static <T extends Resolution> Optional<T> getFirstFailedResolution(T... resolutions) {
		return Arrays.stream(resolutions).filter(resolution -> resolution.failed()).findFirst();
	}

	default boolean failed() {
		return !result().isSuccessful();
	}
}
