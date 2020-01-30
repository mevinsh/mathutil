package io.shubansoft.mathutil.service;

import io.shubansoft.mathutil.calcs.IsAPrimeNumber;
import io.shubansoft.mathutil.model.Primes;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SieveOfEratosthenesPrimeGenerationTest {

    @Autowired
    private SieveOfEratosthenesPrimeGeneration strategy;

    private IsAPrimeNumber isAPrimeNumber;

    @BeforeEach
    void setUp() {
        isAPrimeNumber = new IsAPrimeNumber();
    }

    @Test
    void verify_0IsNotAPrimeNumber() {
        final int limit = 0;
        final Primes primes = strategy.generatePrimes(0);
        assertNotNull(primes);
        assertEquals(primes.getInitial(),limit);
        assertEquals(primes.getPrimes(), Collections.emptySet());
    }

    @Test
    void verify_1IsNotAPrimeNumber() {
        final int limit = 1;
        final Primes primes = strategy.generatePrimes(1);
        assertNotNull(primes);
        assertEquals(primes.getInitial(),limit);
        assertEquals(primes.getPrimes(), Collections.emptySet());
    }

    @Test
    void verify_2IsAPrimeNumber() {
        final int limit = 2;
        final Primes primes = strategy.generatePrimes(2);
        assertNotNull(primes);
        assertEquals(primes.getInitial(),limit);
        assertEquals(primes.getPrimes().size(), 1);
        assertTrue(primes.getPrimes().contains(2L),"2 is a prime number");
    }

    @Test
    void verify_EvenNumbersNotPrime() {
        IntStream.iterate(4, i->i+2)
                .limit(100)
                .forEach(n-> assertTrue( !strategy.generatePrimes(n).getPrimes().contains(Long.valueOf(n)), n + " is not a Prime number"));
    }

    @Test
    void verify_PrimeNumber() {
        Lists.newArrayList(2,3,5,7,11)
                .forEach(n-> assertTrue(strategy.generatePrimes(n).getPrimes().contains(Long.valueOf(n)), n + " is a Prime number"));

    }

    @Test
    void verify_PrimeNumber_largeSet() {
        final Primes primes = strategy.generatePrimes(100000);
        assertNotNull(primes);
        assertEquals(primes.getInitial(),100000);
        primes.getPrimes().forEach(p-> assertTrue(isAPrimeNumber.test(p)));


    }
}