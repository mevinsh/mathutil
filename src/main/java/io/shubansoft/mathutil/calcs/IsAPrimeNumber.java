package io.shubansoft.mathutil.calcs;

import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.LongStream;

public class IsAPrimeNumber implements LongPredicate {

    @Override
    public boolean test(final long number) {
        final Predicate<Long> isDivisible = divisor -> number % divisor == 0;
        return number > 1 &&
                LongStream.range(2,number)
                        .noneMatch(isDivisible::test);
    }
}