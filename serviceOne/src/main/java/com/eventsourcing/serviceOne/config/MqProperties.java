package com.eventsourcing.serviceOne.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kaihe
 *
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mq") // prefix mq, find mq.* values
public class MqProperties {
  
  private String reqQueueServiceOne;

  private String rspQueueOoc;

  private String listenerConcurrency;
}
