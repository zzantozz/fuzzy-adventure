package com.example

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/5/13
 * Time: 12:45 PM
 */
class LogicTest extends Specification {
    def logic = new Logic()

    @Unroll
    def 'sanity test for primeness of #number'() {
        expect:
        logic.isPrime(number)

        where:
        number << [2, 3, 5]
    }

    @Unroll
    def 'sanity test for next prime after #number'() {
        expect:
        logic.nextPrimeFrom(number) == next

        where:
        number | next
        2      | 3
        3      | 5
        5      | 7
    }
}
