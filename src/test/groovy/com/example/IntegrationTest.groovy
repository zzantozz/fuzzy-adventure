package com.example

import groovy.sql.Sql
import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.jms.core.JmsTemplate
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
    @Shared def jms = new JmsTemplate().with {
        connectionFactory = new ActiveMQConnectionFactory(JmsAccessor.BROKER_URI)
        receiveTimeout = 2000
        return it
    }
    @Shared def sql = Sql.newInstance(JdbcAccessor.JDBC_URL)

    def setupSpec() {
        application.start()
    }

    def cleanupSpec() {
        application.stop()
        sql.close()
    }

    def 'sanity check of overall system for first prime'() {
        when:
        jms.convertAndSend('NumberInput', '2')

        then:
        jms.receiveAndConvert('NumberOutput') == '3'
    }

    def 'sanity check of overall system for first non-prime'() {
        when:
        jms.convertAndSend('NumberInput', '4')

        then:
        jms.receiveAndConvert('NumberOutput') == '5'
    }

    def 'sanity check of database storage'() {
        when:
        jms.convertAndSend('NumberInput', '2')

        then:
        jms.receiveAndConvert('NumberOutput') // wait for processing
        sql.rows('select the_number from numbers').collect{ it.the_number }.contains(2L)
    }
}
