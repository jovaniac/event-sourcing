package com.eventsourcing.serviceOne.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
/**
 * @author kaihe
 *
 */
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("file:${app.config.home:src/main/resources}/application_serviceOne.properties")
@EnableJpaAuditing
public class AppConfig {
  @Bean
  public RestTemplate restTemplate() {

    ClientHttpRequestFactory factory =
        new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
    RestTemplate restTemplate = new RestTemplate(factory);
    return restTemplate;
  }
}
