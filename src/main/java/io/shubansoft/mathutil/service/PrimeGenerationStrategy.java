package io.shubansoft.mathutil.service;

import io.shubansoft.mathutil.model.Primes;

import java.util.Collections;

public interface PrimeGenerationStrategy {

    default Primes primesLessThan2(final long limit){
        if(limit >= 2)
            throw new IllegalArgumentException("Invalid argument - limit should be less than 2");
        return Primes.builder()
                    .initial(limit)
                    .primes(Collections.emptySet())
                    .build();
    }

    Primes generatePrimes(long limit);

    Primes generatePrimes(long start, long limit);
}
