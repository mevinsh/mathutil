package io.shubansoft.mathutil.controller;

import io.shubansoft.mathutil.model.Primes;
import io.shubansoft.mathutil.service.PrimesGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@Validated
@Slf4j
public class PrimesController {

    private final PrimesGeneratorService primesGeneratorService;

    @Inject
    public PrimesController(
            final PrimesGeneratorService primesGeneratorService){
        this.primesGeneratorService = primesGeneratorService;
    }

    @GetMapping(path = "/primes/{limit}", produces = { "application/json", "application/xml" })
    public Primes generatePrimes(
            @PathVariable
            @NotNull final Long limit,
            @RequestParam(required = false) final Long start,
            @RequestParam(required = false) final String algorithm){

        if(StringUtils.isEmpty(algorithm)) { //cant use Optional.ofNullable(algorithm) to ensure algorithm can be used as key for cache
            if(start != null)
                return primesGeneratorService.generatePrimes(start,limit);
            return primesGeneratorService.generatePrimes(limit);
        }
        else{
            if(start != null)
                return primesGeneratorService.generatePrimes(start,limit,Optional.of(algorithm));
            return primesGeneratorService.generatePrimes(limit, Optional.of(algorithm));
        }

    }
}
