package io.shubansoft.mathutil.service;

import com.google.common.collect.Sets;
import io.shubansoft.mathutil.calcs.IsAPrimeNumber;
import io.shubansoft.mathutil.model.Primes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service(value = "bruteForcePrimeGeneration")
@Slf4j
public class BruteForcePrimeGeneration implements PrimeGenerationStrategy {

    private final IsAPrimeNumber isAPrimeNumber;

    public BruteForcePrimeGeneration(){
        this.isAPrimeNumber = new IsAPrimeNumber();
    }

    @Override
    public Primes generatePrimes(final long limit) {
        return generatePrimes(2,limit);
    }

    @Override
    public Primes generatePrimes(final long start, final long limit) {
        if( limit < 2 )
            return primesLessThan2(limit);

        log.info(String.format("Generating Primes using BruteForceStrategy from [%d] to [%d]", start, limit));
        return new Primes(limit,
                Sets.newTreeSet(LongStream.rangeClosed(start, limit)
                        .parallel()
                        .filter(isAPrimeNumber)
                        .boxed()
                        .collect(Collectors.toSet())));
    }
}
