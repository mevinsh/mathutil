package io.shubansoft.mathutil.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.shubansoft.mathutil.model.Primes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class PrimesGeneratorService {

    private final PrimeGenerationStrategyFactory primeGenerationStrategyFactory;

    private volatile Primes primesCache;

    @Inject
    public PrimesGeneratorService(
            final PrimeGenerationStrategyFactory primeGenerationStrategyFactory){
        this.primeGenerationStrategyFactory = primeGenerationStrategyFactory;

        this.primesCache = Primes.builder()
                .initial(Long.valueOf(2))
                .primes(new HashSet<>(Arrays.asList(2L)))
                .build();
    }

    @Cacheable(value = "primes-1") //Level 1 Cache
    public Primes generatePrimes(final long limit) {
        return checkCacheAndReturnPrimes(2,limit,Optional.empty());
    }

    @Cacheable(value = "primes-2") //Level 1 Cache
    public Primes generatePrimes(final long start, final long limit) {
        return checkCacheAndReturnPrimes(start,limit,Optional.empty());
    }

    @Cacheable(value = "primes-3") //Level 1 Cache
    public Primes generatePrimes(final long limit, final Optional<String> algorithm) {
        return checkCacheAndReturnPrimes(2,limit, algorithm);
    }

    @Cacheable(value = "primes-4") //Level 1 Cache
    public Primes generatePrimes(final long start, final long limit, final Optional<String> algorithm) {
        return checkCacheAndReturnPrimes(start,limit, algorithm);
    }

    private Primes checkCacheAndReturnPrimes(final long start, final long limit, final Optional<String> algorithm) {

        if(primesCache.getInitial().longValue() < limit){

            synchronized (primesCache){
                if(primesCache.getInitial().longValue() < limit){  //limit should be always greater and not Equals
                            //** Double Checked Locking to ensure after getting monitor the limit is still not available in cache

                    final Set<Long> cachedPrimesSet = primesCache.getPrimes();//sorted
                    final Long maxCachedPrime = Lists.newArrayList(cachedPrimesSet).get(cachedPrimesSet.size()-1);
                    final Primes nonCachedPrimes =
                            primeGenerationStrategyFactory.getStrategy(algorithm)
                                    .generatePrimes(maxCachedPrime,limit);

                    final Set<Long> subsetPrimes = new HashSet<>();
                    final Set<Long> allPrimes = new HashSet<>();
                   cachedPrimesSet.forEach(p->{
                       if(p.compareTo(start) >= 0)
                           subsetPrimes.add(p); //always stores all primes in cache UPTO limit
                       allPrimes.add(p);
                   });
                    nonCachedPrimes.getPrimes().forEach(p->{
                        if(p.compareTo(start) >= 0 && p.compareTo(limit) <=0 )
                            subsetPrimes.add(p);
                        allPrimes.add(p); //always stores all primes in cache UPTO limit
                    });
                    
                    this.primesCache.setPrimes(Sets.newTreeSet(allPrimes));
                    this.primesCache.setInitial(limit);

                    if(start >= 0)
                        return Primes.builder().initial(limit).primes(subsetPrimes).build();
                    else
                        return primesCache;
                }
            }
        }

        synchronized (primesCache){
            final Set<Long> primes = new HashSet<>();
            for (final Long p : primesCache.getPrimes()) {
                final long value = p.longValue();
                if(value >= start && value <= limit)
                    primes.add(p);
            }
            return Primes.builder().initial(limit).primes(Sets.newTreeSet(primes)).build();
        }
    }

}
