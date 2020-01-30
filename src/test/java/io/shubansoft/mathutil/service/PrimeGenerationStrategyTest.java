package io.shubansoft.mathutil.service;

import io.shubansoft.mathutil.model.Primes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PrimeGenerationStrategyTest {

    @Autowired
    private PrimeGenerationStrategy bruteForcePrimeGeneration;

    @Autowired
    private PrimeGenerationStrategy sieveOfEratosthenesPrimeGeneration;

    private Primes getPrimesPerStrategy(final PrimeGenerationStrategy strategy, final long limit){
        return strategy.generatePrimes(limit);
    }

    @Test
    void verify_primesLessThan2() {
        Arrays.asList(-2L,-1L,0L,1L).forEach(limit->{
            final Primes primesBrute = getPrimesPerStrategy(bruteForcePrimeGeneration,limit);
            final Primes primesSieve = getPrimesPerStrategy(sieveOfEratosthenesPrimeGeneration,limit);

            assertNotNull(primesBrute);
            assertEquals(primesBrute.getInitial(),limit);
            assertEquals(primesBrute.getPrimes(), Collections.emptySet());

            assertNotNull(primesSieve);
            assertEquals(primesSieve.getInitial(),limit);
            assertEquals(primesSieve.getPrimes(), Collections.emptySet());
        });

    }

    @Test
    void verify_primesGTorEq2() {
        assertThrows(IllegalArgumentException.class,() -> {
            bruteForcePrimeGeneration.primesLessThan2( 2L);
        });
        assertThrows(IllegalArgumentException.class,() -> {
            sieveOfEratosthenesPrimeGeneration.primesLessThan2( 2L);
        });
    }
}