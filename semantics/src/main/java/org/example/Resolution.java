package org.example;

import java.util.Arrays;
import java.util.Optional;

public interface Resolution {
	Result result();

	static <T extends Resolution> Optional<T> returnFirstFailedResolution(T... resolutions) {
		return Arrays.stream(resolutions)
				.filter(resolution -> !resolution.result().isSuccessful())
				.findFirst();
	}
}
