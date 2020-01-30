package io.shubansoft.mathutil.service;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class PrimeGenerationStrategyFactory implements StrategyFactory {

    private final BruteForcePrimeGeneration bruteForcePrimeGeneration;

    private final SieveOfEratosthenesPrimeGeneration sieveOfEratosthenesPrimeGeneration;

    public PrimeGenerationStrategyFactory(
            final BruteForcePrimeGeneration bruteForcePrimeGeneration,
            final SieveOfEratosthenesPrimeGeneration sieveOfEratosthenesPrimeGeneration) {
        this.bruteForcePrimeGeneration = bruteForcePrimeGeneration;
        this.sieveOfEratosthenesPrimeGeneration = sieveOfEratosthenesPrimeGeneration;
    }

    @Override
    public PrimeGenerationStrategy getStrategy(final Optional<String> algorithm) {
        if(algorithm.isPresent() && algorithm.get().contains("sieve"))
            return sieveOfEratosthenesPrimeGeneration;

        return bruteForcePrimeGeneration;

    }
}
