package com.eventsourcing.eventsourcinglib.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eventsourcing.eventsourcinglib.exception.ValidationException;
import com.eventsourcing.eventsourcinglib.service.MessageService;

@Component
public class RequestHeaderValidator {
  @Autowired
  protected MessageService messageService;

  public void validate(String value, String msgCode) {
    if (StringUtils.isEmpty(value)) {
      throw new ValidationException(msgCode, messageService.getMessage(msgCode));
    }
    return;
  }
}
