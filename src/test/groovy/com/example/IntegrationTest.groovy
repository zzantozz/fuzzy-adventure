package com.example

import spock.lang.Shared
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/5/13
 * Time: 11:06 AM
 */
class IntegrationTest extends Specification {
    @Shared def application = new Application()
    def jms = new JmsAccessor()

    def setupSpec() {
        application.start()
    }

    def cleanupSpec() {
        application.stop()
    }

    def 'sanity check of overall system for first prime'() {
        when:
        jms.sendToNumberInput(2)

        then:
        jms.receiveFromNumberOutput() == 3
    }

    def 'sanity check of overall system for first non-prime'() {
        when:
        jms.sendToNumberInput(4)

        then:
        jms.receiveFromNumberOutput() == 5
    }
}
