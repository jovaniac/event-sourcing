package com.eventsourcing.eventsourcinglib.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SystemIDValidator implements ConstraintValidator<SystemID, String> {

  // You can also inject service and check if application id exist or not

  public void initialize(SystemID constraintAnnotation) {}

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !StringUtils.isBlank(value);
  }

}
