package com.eventsourcing.eventsourcinglib.mq.data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class MqRequestCommand {

  private CommandType type;

  @Enumerated(EnumType.STRING)
  private CommandValue value;

}
