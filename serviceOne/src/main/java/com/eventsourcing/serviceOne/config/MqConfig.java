package com.eventsourcing.serviceOne.config;

import javax.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.support.converter.MessageConverter;
import com.eventsourcing.eventsourcinglib.mq.MqMessageConverter;
import lombok.Generated;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author HEKAI27
 *
 */

@Generated
@Configuration
@EnableJms
@RequiredArgsConstructor
public class MqConfig implements JmsListenerConfigurer {
  @NonNull
  private MqProperties mqProperties;

  @NonNull
  private ConnectionFactory connectionFactory;

  @NonNull
  private MqMessageConverter mqMessageConverter;

  @Override
  public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
    registrar.setContainerFactory(containerFactory());
  }

  @Bean
  public JmsListenerContainerFactory<?> containerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setConcurrency(mqProperties.getListenerConcurrency());
    factory.setMessageConverter(mqMessageConverter);
    return factory;
  }
}
