package io.shubansoft.mathutil.calcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsAPrimeNumberTest {

    private IsAPrimeNumber isPrime;

    @BeforeEach
    public void setup(){
        isPrime = new IsAPrimeNumber();
    }

    @Test
    void verify_1IsNotAPrimeNumber() {
        assertFalse(isPrime.test(1L),"1 is not a prime number");
    }

    @Test
    void verify_2IsAPrimeNumber() {
        assertTrue(isPrime.test(2l),"2 is a prime number");
    }

    @Test
    void verify_EvenNumbersNotPrime() {
        LongStream.iterate(4, i->i+2).limit(100)
                .forEach(n-> assertFalse(isPrime.test(n)));
    }

    @Test
    void verify_PrimeNumber() {
        assertTrue(isPrime.test(3L),"3 is a prime number");
        assertTrue(isPrime.test(5L),"5 is a prime number");
        assertTrue(isPrime.test(7L),"7 is a prime number");
        assertTrue(isPrime.test(11L),"11 is a prime number");
        assertTrue(isPrime.test(13L),"13 is a prime number");
    }
}