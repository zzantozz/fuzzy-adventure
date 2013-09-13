package com.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/5/13
 * Time: 10:14 AM
 */
public class JmsAccessor {
    public static final String BROKER_URI = "vm://bob?broker.persistent=false";
    private ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(BROKER_URI);
    private JmsTemplate jmsTemplate = new JmsTemplate(cf) {{
        setReceiveTimeout(5000);
    }};
    private List<DefaultMessageListenerContainer> containers = new ArrayList<>();

    public void stop() {
        for (DefaultMessageListenerContainer container : containers) {
            container.destroy();
        }
    }

    public void receiveMessagesFromQueueToMethod(String queueName, Object targetObject, String targetMethod) {
        receiveMessagesFromQueueToMethod(queueName, targetObject, targetMethod, 1);
    }

    public void receiveMessagesFromQueueToMethod(String queueName, Object targetObject, String targetMethod, int concurrentConsumers) {
        MessageListenerAdapter messageListener = new MessageListenerAdapter(targetObject);
        messageListener.setMessageConverter(null);
        messageListener.setDefaultListenerMethod(targetMethod);
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(cf);
        container.setDestinationName(queueName);
        container.setMessageListener(messageListener);
        container.setConcurrentConsumers(concurrentConsumers);
        container.afterPropertiesSet();
        container.start();
        containers.add(container);
    }

    public void sendToNumberInput(long number) {
        jmsTemplate.convertAndSend("NumberInput", String.valueOf(number));
    }

    public void sendToPrimes(long number, boolean inputWasPrime) {
        jmsTemplate.convertAndSend("Primes", String.valueOf(number), new MarkInputPrimesPostProcessor(inputWasPrime));
    }

    public void sendToNumberOutput(long number) {
        jmsTemplate.convertAndSend("NumberOutput", String.valueOf(number));
    }

    public long receiveFromNumberOutput() {
        String output = (String) jmsTemplate.receiveAndConvert("NumberOutput");
        return Long.parseLong(output);
    }

    private static class MarkInputPrimesPostProcessor implements MessagePostProcessor {
        private boolean inputWasPrime;

        public MarkInputPrimesPostProcessor(boolean inputWasPrime) {
            this.inputWasPrime = inputWasPrime;
        }

        @Override
        public Message postProcessMessage(Message message) throws JMSException {
            message.setBooleanProperty("InputWasPrime", inputWasPrime);
            return message;
        }
    }
}
