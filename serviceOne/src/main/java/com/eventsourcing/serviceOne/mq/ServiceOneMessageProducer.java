package com.eventsourcing.serviceOne.mq;

import javax.jms.JMSException;
import org.springframework.stereotype.Component;
import com.eventsourcing.eventsourcinglib.mq.MqMessageProducer;
import com.eventsourcing.eventsourcinglib.mq.data.MqResponse;
import com.eventsourcing.serviceOne.config.MqProperties;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Builder
public class ServiceOneMessageProducer {
  
  @NonNull
  private MqMessageProducer mqMessageProducer;

  @NonNull
  private MqProperties mqProperties;

  public void sendOocResponse(String messageId, MqResponse msgResponse) throws JMSException {
    mqMessageProducer.replyMessage(mqProperties.getRspQueueOoc(), messageId, msgResponse);
  }
}
