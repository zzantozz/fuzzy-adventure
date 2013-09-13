package com.example

import spock.lang.Shared
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/5/13
 * Time: 11:06 AM
 */
class JdbcAccessorTest extends Specification {
    @Shared def accessor = new JdbcAccessor()

    def setupSpec() {
        accessor.init()
    }

    def 'insert and get works'() {
        when:
        accessor.insert(1)
        accessor.insert(2)
        accessor.insert(3)
        accessor.insert(4)

        and:
        def loaded = accessor.getInsertedNumbers()

        then:
        loaded == [1, 2, 3, 4]
    }

    def 'insert has artificial delay built in'() {
        when:
        long start = System.currentTimeMillis()
        accessor.insert(1234)
        long elapsed = System.currentTimeMillis() - start

        then:
        elapsed >= 500
    }
}
