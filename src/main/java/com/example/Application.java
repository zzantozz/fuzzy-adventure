package com.example;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class Application {
    private JmsAccessor jmsAccessor = new JmsAccessor();
    private JdbcAccessor jdbcAccessor = new JdbcAccessor();
    private Logic logic = new Logic();

    public void start() {
        jdbcAccessor.init();
        jmsAccessor.receiveMessagesFromQueueToMethod("NumberInput", this, "handleNumberInput");
        jmsAccessor.receiveMessagesFromQueueToMethod("Primes", this, "handlePrimes", 50);
    }

    public void stop() {
        jmsAccessor.stop();
    }

    public void handleNumberInput(TextMessage message) throws JMSException {
        long number = Long.parseLong(message.getText());
        if (logic.isPrime(number)) {
            jmsAccessor.sendToPrimes(number, true);
        } else {
            jmsAccessor.sendToPrimes(logic.nextPrimeFrom(number), false);
        }
    }

    public void handlePrimes(TextMessage message) throws JMSException {
        long number = Long.parseLong(message.getText());
        jdbcAccessor.insert(number);
        boolean inputWasPrime = message.getBooleanProperty("InputWasPrime");
        if (inputWasPrime) {
            jmsAccessor.sendToNumberOutput(number + 1);
        } else {
            jmsAccessor.sendToNumberOutput(number);
        }
    }

    public void printMessageText(TextMessage message) throws JMSException {
        System.out.println(message.getText());
    }

    public static void main(String[] args) throws Exception {
        // Start the application
        Application application = new Application();
        application.start();
        // Watch for output
        application.jmsAccessor.receiveMessagesFromQueueToMethod("NumberOutput", application, "printMessageText");
        // Send some sample input
        application.jmsAccessor.sendToNumberInput(2);
        application.jmsAccessor.sendToNumberInput(4);
        application.jmsAccessor.sendToNumberInput(7);
        // Wait for the input to process
        Thread.sleep(2500);
        // Check the contents of the database
        System.out.println("In the database: " + application.jdbcAccessor.getInsertedNumbers());
        // Shut down
        application.stop();
    }
}