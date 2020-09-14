/**
 * 
 */
package com.eventsourcing.serviceOne.service;

import org.springframework.stereotype.Service;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kaihe
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Builder
public class ServiceOneServiceImpl implements ServiceOneService {

  @Override
  public void doSomething(String systemID) {
    // TODO Auto-generated method stub
    
  }

}
