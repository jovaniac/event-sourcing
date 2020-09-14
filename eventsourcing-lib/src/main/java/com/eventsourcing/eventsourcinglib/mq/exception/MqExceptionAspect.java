package com.eventsourcing.eventsourcinglib.mq.exception;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.eventsourcing.eventsourcinglib.domain.ProcessStatus;
import com.eventsourcing.eventsourcinglib.exception.ApplicationException;
import com.eventsourcing.eventsourcinglib.exception.BaseException;
import com.eventsourcing.eventsourcinglib.exception.ValidationException;
import com.eventsourcing.eventsourcinglib.mq.data.ErrorType;
import com.eventsourcing.eventsourcinglib.mq.data.MqRequest;
import com.eventsourcing.eventsourcinglib.mq.data.MqResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class MqExceptionAspect {
  @NonNull
  private MqMessageProducer mqMessageProducer;
  
  /**
   * AfterThrowing pointcut captures exception where has @HandleMqException annotation.
   */
  @AfterThrowing(value = "@annotation(com.demo.microservices.servicelibs.aop.HandleMqException)",
      throwing = "ex")
  public void handleMqException(JoinPoint joinPoint, Exception ex) throws Throwable {
    log.error("AOP get the exception: ", ex);
  }

  @Around(value = "@annotation(com.td.mboa.common.mq.exception.HandleMqException)")
  public Object handleMqException(ProceedingJoinPoint point) throws Throwable {
    try {
      point.proceed();
    } catch (Throwable e) {
      log.error("MqExceptionAspect.handleMqException(): " + e.toString());

      MqResponse errorResponse =
          MqResponse.builder().responseDateTime(LocalDateTime.now()).build();

      // Get Request
      Object[] args = point.getArgs();
      for (int i = 0; i < args.length; i++) {
        if (args[i] instanceof MqRequest) {
          errorResponse.setRequest((MqRequest) args[i]);
        }
      }

      Method method = MethodSignature.class.cast(point.getSignature()).getMethod();
      HandleMqException handleMqExceptionAnnotation = method.getAnnotation(HandleMqException.class);

      // Get service name
      String serviceName = handleMqExceptionAnnotation.serviceName();
      errorResponse.setServiceName(serviceName);

      // Get Error related information
      errorResponse.setStatus(ProcessStatus.FAILED);
      if (e instanceof BaseException) {
        ErrorType errorType = ErrorType.SYSTEM;
        if (e instanceof ValidationException) {
          errorType = ErrorType.VALIDATION;
        } else if (e instanceof ApplicationException) {
          errorType = ErrorType.APPLICATION;
        }

        errorResponse.setErrorType(errorType);
        errorResponse.setStatusCode(((BaseException) e).getServerStatusCode());
      } else {
        String errorStatusCode = handleMqExceptionAnnotation.generalServerErrorID();
        errorResponse.setErrorType(ErrorType.SYSTEM);
        errorResponse.setStatusCode(errorStatusCode);
      }

      // Set to Error Queue
      String errorQueueName = handleMqExceptionAnnotation.errorQueueName();
      mqMessageProducer.sendMessage(errorQueueName, errorResponse);
    }
    return point;
  }
}
