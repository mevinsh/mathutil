package io.shubansoft.mathutil.service;

import io.shubansoft.mathutil.model.Primes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PrimesGeneratorServiceTest {

    private PrimesGeneratorService primesGeneratorService;

    @Autowired
    private PrimeGenerationStrategyFactory primeGenerationStrategyFactory;

    @BeforeEach
    void setUp() {
        primesGeneratorService = new PrimesGeneratorService(primeGenerationStrategyFactory);
    }

    @Test
    void verify_generatePrimes_10lLimit(){
        final Primes response = primesGeneratorService.generatePrimes(10L);
        assertNotNull(response.getInitial());
        assertEquals(response.getInitial().longValue(),10L);
        assertTrue(response.getPrimes().contains(2L));
        assertTrue(response.getPrimes().contains(3L));
        assertTrue(response.getPrimes().contains(5L));
        assertTrue(response.getPrimes().contains(7L));

    }

    @Test
    public void verify_primesLimit10_WithStart5(){
        final Primes response = primesGeneratorService.generatePrimes(5L,10L);
        assertNotNull(response.getInitial());
        assertEquals(response.getInitial().longValue(),10L);
        assertFalse(response.getPrimes().contains(2L));
        assertFalse(response.getPrimes().contains(3L));
        assertTrue(response.getPrimes().contains(5L));
        assertTrue(response.getPrimes().contains(7L));
    }

    @Test
    public void verify_primesLimit10_with5Start_WithSieveAlgo(){
        final Primes response = primesGeneratorService.generatePrimes(5L,10L,Optional.of("sieve"));
        assertNotNull(response.getInitial());
        assertEquals(response.getInitial().longValue(),10L);
        assertFalse(response.getPrimes().contains(2L));
        assertFalse(response.getPrimes().contains(3L));
        assertTrue(response.getPrimes().contains(5L));
        assertTrue(response.getPrimes().contains(7L));
    }

    @Test
    public void verify_primesLimit10_with5Start_WithBruteAlgo(){
        final Primes response = primesGeneratorService.generatePrimes(5L,10L,Optional.of("brute"));
        assertNotNull(response.getInitial());
        assertEquals(response.getInitial().longValue(),10L);
        assertFalse(response.getPrimes().contains(2L));
        assertFalse(response.getPrimes().contains(3L));
        assertTrue(response.getPrimes().contains(5L));
        assertTrue(response.getPrimes().contains(7L));
    }

    @Test
    public void verify_primesLimit10_cacheresults(){
        final Primes response1 = primesGeneratorService.generatePrimes(10L);
        assertNotNull(response1.getInitial());
        assertEquals(response1.getInitial().longValue(),10L);
        assertTrue(response1.getPrimes().contains(2L));
        assertTrue(response1.getPrimes().contains(3L));
        assertTrue(response1.getPrimes().contains(5L));
        assertTrue(response1.getPrimes().contains(7L));

        //second call will return result from cache
        final Primes response2 = primesGeneratorService.generatePrimes(10L);
        assertNotNull(response2.getInitial());
        assertEquals(response2.getInitial().longValue(),10L);
        assertTrue(response2.getPrimes().contains(2L));
        assertTrue(response2.getPrimes().contains(3L));
        assertTrue(response2.getPrimes().contains(5L));
        assertTrue(response2.getPrimes().contains(7L));
    }

    @Test
    public void verify_primesLimit10_cachefaster(){
        final long startNanos1 = System.nanoTime();
        final Primes response1 = primesGeneratorService.generatePrimes(10000L);
        final long endNanos1 = System.nanoTime();

        //second call will return result from cache
        final long startNanos2 = System.nanoTime();
        final Primes response2 = primesGeneratorService.generatePrimes(10000L);
        final long endNanos2 = System.nanoTime();

        assertTrue(getTimeMillis(startNanos2,endNanos2) < getTimeMillis(startNanos1,endNanos1));
    }

    private Long getTimeMillis(final long startNanos, final long endNanos){
        final long timeNanos = endNanos - startNanos;
        final long timeMillis = timeNanos/1000000;
        return timeMillis;
    }


}