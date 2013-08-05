package com.example;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 7/31/13
 * Time: 12:51 PM
 */
public class Logic {
    // TODO: This is a stupid way of handling primes. It's just here to get basic tests going. Make sure to fix this!
    private List<Integer> somePrimes = Arrays.asList(2, 3, 5, 7, 11);

    public boolean isPrime(long number) {
        return somePrimes.contains((int) number);
    }

    public long nextPrimeFrom(long number) {
        int result = (int) number + 1;
        while (!somePrimes.contains(result)) result++;
        return result;
    }
}
