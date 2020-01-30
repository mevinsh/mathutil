package io.shubansoft.mathutil.service;

import com.google.common.collect.Sets;
import io.shubansoft.mathutil.model.Primes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.toIntExact;

@Service(value = "sieveOfEratosthenesPrimeGeneration")
@Slf4j
public class SieveOfEratosthenesPrimeGeneration implements PrimeGenerationStrategy {

    private static final Long SIEVE_STARTING_PRIME = 2L;

    @Override
    public Primes generatePrimes(long limit) {
        return generatePrimes(2,limit);
    }

    @Override
    public Primes generatePrimes(long start, long limit) {
        if( limit < 2 )
            return primesLessThan2(limit);

        log.info("Generating Primes using SieveOfEratosthenesStrategy from ["+start+"] to ["+limit+"]");
        final int startIntVal = toIntExact(start);
        final int limitIntVal = toIntExact(limit);
        final boolean prime[] = new boolean[limitIntVal + 1];
        Arrays.fill(prime, true);
        for (long p = SIEVE_STARTING_PRIME; p * p <= limit; p++) {
            if (prime[toIntExact(p)]) {
                for (long i = p * SIEVE_STARTING_PRIME; i <= limit; i += p) {
                    prime[toIntExact(i)] = false;
                }
            }
        }

        final Set<Long> primeSet = new HashSet<>();
        for (long i = start; i <= limit; i++) {
            if (prime[toIntExact(i)]) {
                primeSet.add(i);
            }
        }
        return Primes.builder().initial(limit).primes(Sets.newTreeSet(primeSet)).build();

    }

}
