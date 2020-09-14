package com.eventsourcing.eventsourcinglib.mq;

import java.util.concurrent.atomic.AtomicReference;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import lombok.Builder;
import lombok.Generated;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Generated
@RequiredArgsConstructor
@Component
@Builder
@Slf4j
public class MqMessageProducer {
  @NonNull
  private JmsTemplate jmsTemplate;

  @NonNull
  private MqMessageConverter mqMessageConverter;

  // Send message and create a messageID
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

  // Reply message with correlationID
  public void replyMessage(String queueName, String correlationID, Object mqMessage)
      throws JMSException {
    jmsTemplate.send(queueName, new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        Message retMsg = mqMessageConverter.toMessage(mqMessage, session);
        if (!StringUtils.isBlank(correlationID)) {
          retMsg.setJMSCorrelationID(correlationID);
        }
        return retMsg;
      }
    });
    log.debug("Sent message replied to {} with correlationID: {} ", queueName, correlationID);
  }
}
