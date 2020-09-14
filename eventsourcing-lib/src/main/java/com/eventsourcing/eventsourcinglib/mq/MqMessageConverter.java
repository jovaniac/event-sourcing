package com.eventsourcing.eventsourcinglib.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import com.eventsourcing.eventsourcinglib.mq.data.MqRequest;
import com.eventsourcing.eventsourcinglib.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MqMessageConverter implements MessageConverter {

  @Override
  public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
    String json;
    try {
      json = JsonUtil.object2Json(object);
    } catch (Exception e) {
      throw new MessageConversionException("Message cannot be parsed. ", e);
    }

    TextMessage message = session.createTextMessage();
    message.setText(json);

    return message;
  }

  @Override
  public Object fromMessage(Message message) throws JMSException, MessageConversionException {
    return ((TextMessage) message).getText();
  }

  public static MqRequest convertToMqResponse(TextMessage textMessage)
      throws JMSException {
    return textMessage == null ? null
        : JsonUtil.json2Object(textMessage.getText(), MqRequest.class);
  }
}
