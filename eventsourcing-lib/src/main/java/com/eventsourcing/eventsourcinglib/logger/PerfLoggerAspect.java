/**
 * 
 */
package com.eventsourcing.eventsourcinglib.logger;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * PerfLoggerAspect implements a cross-cutting logging concern which handles performance and
 * entry/exit method logging.
 * 
 * @author kaihe
 *
 */
@Component
@Aspect
public class PerfLoggerAspect {
  // initialize performance logger
  private static final Logger logger = LogManager.getLogger("com.eventsourcing.eventsourcinglib.logger");

  /**
   * Around pointcut captures a method execution where the executing method has @PerfLog annotation.
   */
  @Around(value = "@annotation(com.td.mboa.common.logger.PerfLog)")
  public Object around(ProceedingJoinPoint point) throws Throwable {

    Signature signature = point.getStaticPart().getSignature();
    String args = ArrayUtils.isEmpty(point.getArgs()) ? "" : Arrays.deepToString(point.getArgs());

    // capture execution time
    StopWatch watch = new StopWatch();
    watch.start();
    Object result = point.proceed();
    watch.stop();

    // write message to the log file
    Method method = MethodSignature.class.cast(point.getSignature()).getMethod();
    PerfLog loggableAnnotation = method.getAnnotation(PerfLog.class);
    Level level = Level.getLevel(loggableAnnotation.level());
    String ignoreParams = loggableAnnotation.ignoreParams();

    if (logger.isEnabled(level)) {

      StringBuilder msg = new StringBuilder();
      msg.append("calling: ").append(signature.getDeclaringTypeName()).append('.')
          .append(signature.getName());
      // check if params need to be ignored
      if (ignoreParams.equalsIgnoreCase("FALSE")) {
        msg.append("\n args: ").append(args);
      }

      msg.append("\n execution time: ").append(watch.getTotalTimeMillis()).append(" ms");
      logger.log(level, msg);
    }
    return result;
  }
}
