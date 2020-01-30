package io.shubansoft.mathutil.service;

import java.util.Optional;

public interface StrategyFactory {

    PrimeGenerationStrategy getStrategy(final Optional<String> algorithm);
}
