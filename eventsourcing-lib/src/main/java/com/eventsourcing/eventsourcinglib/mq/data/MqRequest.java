package com.eventsourcing.eventsourcinglib.mq.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MqRequest {

  private String systemId;

  private MqRequestCommand command;

  private RequestParty requestedBy;
}
