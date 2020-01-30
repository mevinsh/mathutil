package io.shubansoft.mathutil;

import io.shubansoft.mathutil.model.Primes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MathutilIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_generatePrimes10() throws Exception {
        final ResponseEntity<Primes> response =
                restTemplate.getForEntity(new URL("http://localhost:"+port+"/primes/10").toString(),
                        Primes.class);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getInitial());
        assertNotNull(response.getBody().getPrimes());
        assertEquals(response.getBody().getInitial(),10L);
        assertEquals(response.getBody().getPrimes().size(),4);


    }

    @Test
    public void test_generatePrimes5to10() throws Exception {
        final ResponseEntity<Primes> response =
                restTemplate.getForEntity(new URL("http://localhost:"+port+"/primes/10?start=5").toString(),
                        Primes.class);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getInitial());
        assertNotNull(response.getBody().getPrimes());
        assertEquals(response.getBody().getInitial(),10L);
        assertEquals(response.getBody().getPrimes().size(),2);


    }

    @Test
    public void test_generatePrimes10Brute() throws Exception {
        final ResponseEntity<Primes> response =
                restTemplate.getForEntity(new URL("http://localhost:"+port+"/primes/10?algorithm=brute").toString(),
                        Primes.class);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getInitial());
        assertNotNull(response.getBody().getPrimes());
        assertEquals(response.getBody().getInitial(),10L);
        assertEquals(response.getBody().getPrimes().size(),4);
    }

    @Test
    public void test_generatePrimes10Sieve() throws Exception {
        final ResponseEntity<Primes> response =
                restTemplate.getForEntity(new URL("http://localhost:"+port+"/primes/10?algorithm=sieve").toString(),
                        Primes.class);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getInitial());
        assertNotNull(response.getBody().getPrimes());
        assertEquals(response.getBody().getInitial(),10L);
        assertEquals(response.getBody().getPrimes().size(),4);
    }


}
