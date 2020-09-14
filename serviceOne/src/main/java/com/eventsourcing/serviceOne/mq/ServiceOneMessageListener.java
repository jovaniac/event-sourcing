package com.eventsourcing.serviceOne.mq;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.eventsourcing.eventsourcinglib.logger.PerfLog;
import com.eventsourcing.eventsourcinglib.mq.MqMessageConverter;
import com.eventsourcing.eventsourcinglib.mq.data.MqRequest;
import com.eventsourcing.eventsourcinglib.mq.exception.HandleMqException;
import com.eventsourcing.serviceOne.service.ServiceOneService;
import com.eventsourcing.serviceOne.utils.ServiceOneConstatns;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kaihe
 *
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class ServiceOneMessageListener {
  @NonNull
  private ServiceOneMessageProducer serviceOneMessageProducer;

  @NonNull
  private ServiceOneService serviceOneService;

  @PerfLog
  @HandleMqException(serviceName = ServiceOneConstatns.SERVICE_NAME, generalServerErrorID = ServiceOneConstatns.DEFAULT_GENERAL_SERVICE_ERROR_ID)
  @JmsListener(destination = "${mq.req-queue-service1}", containerFactory = "jmsListenerContainerFactory")
  public void receiveMessage(@Header(JmsHeaders.MESSAGE_ID) String messageId,
      TextMessage textMessage) throws JMSException {
    MqRequest request = MqMessageConverter.convertToMqResponse(textMessage);
    log.info("messageId: {} Retrive and Save for SystemId {}", messageId, request.getSystemId());
    serviceOneService.doSomething(request.getSystemId());
    
    //TODO: Convert and sent Response
    serviceOneMessageProducer.sendOocResponse(messageId, null);
  }
}
