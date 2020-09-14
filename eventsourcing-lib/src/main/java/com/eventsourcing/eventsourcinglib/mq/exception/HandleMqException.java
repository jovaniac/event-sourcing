package com.eventsourcing.eventsourcinglib.mq.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandleMqException {

  String serviceName();

  String generalServerErrorID();

  String errorQueueName() default "DEMO.ERROR.RES"; // ERROR Queue
}
