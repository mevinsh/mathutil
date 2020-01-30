package io.shubansoft.mathutil.controller;

import com.google.common.collect.Sets;
import io.shubansoft.mathutil.model.Primes;
import io.shubansoft.mathutil.service.PrimesGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PrimesControllerTest {

    @InjectMocks
    private PrimesController primesController;

    @Mock
    private PrimesGeneratorService primesGeneratorService;

    public static final Set<Long> primesLimit102Start = new HashSet<>();
    public static final Set<Long> primesLimit105Start  = new HashSet<>();

    static {
        primesLimit102Start.add(2L);
        primesLimit102Start.add(3L);
        primesLimit102Start.add(5L);
        primesLimit102Start.add(7L);

        primesLimit105Start.add(5L);
        primesLimit105Start.add(7L);
    }

    @BeforeEach
    void setUp() {
        when(primesGeneratorService.generatePrimes(10L))
                .thenReturn(Primes.builder().initial(10L)
                        .primes(Sets.newTreeSet(primesLimit102Start))
                        .build());
        when(primesGeneratorService.generatePrimes(5L,10L))
                .thenReturn(Primes.builder().initial(10L)
                        .primes(Sets.newTreeSet(primesLimit105Start))
                        .build());
        when(primesGeneratorService.generatePrimes(5L,10L, Optional.of("sieve")))
                .thenReturn(Primes.builder().initial(10L)
                        .primes(Sets.newTreeSet(primesLimit105Start))
                        .build());
        when(primesGeneratorService.generatePrimes(5L,10L, Optional.of("brute")))
                .thenReturn(Primes.builder().initial(10L)
                        .primes(Sets.newTreeSet(primesLimit105Start))
                        .build());
        when(primesGeneratorService.generatePrimes(1L))
                .thenReturn(Primes.builder().initial(1L)
                        .primes(Collections.emptySet())
                        .build());

    }

    @Test
    public void verify_primesLimit10(){
        final Primes response = primesController.generatePrimes(10L,null,null);
        assertNotNull(response.getInitial());
        assertEquals(response.getInitial().longValue(),10L);
        assertTrue(response.getPrimes().contains(2L));
        assertTrue(response.getPrimes().contains(3L));
        assertTrue(response.getPrimes().contains(5L));
        assertTrue(response.getPrimes().contains(7L));
    }

    @Test
    public void verify_primesLimit10_WithStart5(){
        final Primes response = primesController.generatePrimes(10L,5L,null);
        assertNotNull(response.getInitial());
        assertEquals(response.getInitial().longValue(),10L);
        assertFalse(response.getPrimes().contains(2L));
        assertFalse(response.getPrimes().contains(3L));
        assertTrue(response.getPrimes().contains(5L));
        assertTrue(response.getPrimes().contains(7L));
    }

    @Test
    public void verify_primesLimit10_with5Start_WithSieveAlgo(){
        final Primes response = primesController.generatePrimes(10L,5L,"sieve");
        assertNotNull(response.getInitial());
        assertEquals(response.getInitial().longValue(),10L);
        assertFalse(response.getPrimes().contains(2L));
        assertFalse(response.getPrimes().contains(3L));
        assertTrue(response.getPrimes().contains(5L));
        assertTrue(response.getPrimes().contains(7L));
    }

    @Test
    public void verify_primesLimit10_with5Start_WithBruteAlgo(){
        final Primes response = primesController.generatePrimes(10L,5L,"brute");
        assertNotNull(response.getInitial());
        assertEquals(response.getInitial().longValue(),10L);
        assertFalse(response.getPrimes().contains(2L));
        assertFalse(response.getPrimes().contains(3L));
        assertTrue(response.getPrimes().contains(5L));
        assertTrue(response.getPrimes().contains(7L));
    }

    @Test
    public void verify_primesLimit1(){
        final Primes response = primesController.generatePrimes(1L,null,null);
        assertNotNull(response.getInitial());
        assertEquals(response.getInitial().longValue(),1L);
        assertFalse(response.getPrimes().contains(2L));
        assertFalse(response.getPrimes().contains(3L));
        assertFalse(response.getPrimes().contains(5L));
        assertFalse(response.getPrimes().contains(7L));
    }
}