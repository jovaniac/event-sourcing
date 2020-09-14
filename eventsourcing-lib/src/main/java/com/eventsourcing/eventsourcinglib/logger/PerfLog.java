/**
 * 
 */
package com.eventsourcing.eventsourcinglib.logger;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PerfLog annotation - when added to a method, causes a simple performance metric to be written to
 * performance log, which captures the method's execution time. It also captures methods entry/exit.
 *
 * @author kaihe
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PerfLog {
  String logger() default "com.eventsourcing.eventsourcinglib.logger";

  String level() default INFO;

  String ignoreParams() default FALSE;

  /**
   * Log levels
   */
  String OFF = "OFF";
  String FATAL = "FATAL";
  String ERROR = "ERROR";
  String WARN = "WARN";
  String INFO = "INFO";
  String DEBUG = "DEBUG";
  String TRACE = "TRACE";

  /**
   * Ignore params
   */
  String FALSE = "FALSE";
  String TRUE = "TRUE";

}
