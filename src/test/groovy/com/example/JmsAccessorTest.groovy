package com.example

import spock.lang.Specification

import javax.jms.TextMessage

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/5/13
 * Time: 1:09 PM
 */
class JmsAccessorTest extends Specification {
    def accessor = new JmsAccessor()
    def numberInputs = []
    def primes = []
    def numberOutputs = []

    void inputs(TextMessage message) {
        numberInputs << message.text
    }

    void primes(TextMessage message) {
        primes << message.text
    }

    void outputs(TextMessage message) {
        numberOutputs << message.text
    }

    def setup() {
        accessor.receiveMessagesFromQueueToMethod('NumberInput', this, 'inputs')
        accessor.receiveMessagesFromQueueToMethod('Primes', this, 'primes')
        accessor.receiveMessagesFromQueueToMethod('NumberOutput', this, 'outputs')
    }

    def cleanup() {
        accessor.stop()
    }

    def 'sending input works'() {
        when:
        accessor.sendToNumberInput(1)
        accessor.sendToNumberInput(2)
        accessor.sendToNumberInput(3)
        accessor.sendToNumberInput(4)
        sleep 100

        then:
        numberInputs == ['1', '2', '3', '4']
    }

    def 'sending output works'() {
        when:
        accessor.sendToNumberOutput(1)
        accessor.sendToNumberOutput(2)
        accessor.sendToNumberOutput(3)
        accessor.sendToNumberOutput(4)
        sleep 100

        then:
        numberOutputs == ['1', '2', '3', '4']
    }
}
