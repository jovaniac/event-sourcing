package com.eventsourcing.eventsourcinglib.mq.exception;

import java.util.concurrent.atomic.AtomicReference;
import javax.jms.JMSException;
import javax.jms.Message;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class MqMessageProducer {

  @NonNull
  private JmsTemplate jmsTemplate;

  public String sendMessage(String queueName, Object mqMessage) throws JMSException {
    final AtomicReference<Message> message = new AtomicReference<>();
    jmsTemplate.convertAndSend(queueName, mqMessage, messagePostProcessor -> {
      message.set(messagePostProcessor);
      return messagePostProcessor;
    });
    log.debug("Sent message to " + queueName + " with " + message.get().getJMSMessageID());
    log.debug("Message: " + mqMessage.toString());
    return message.get().getJMSMessageID();
  }
}
