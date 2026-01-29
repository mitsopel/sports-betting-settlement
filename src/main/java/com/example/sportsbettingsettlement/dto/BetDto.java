package com.example.sportsbettingsettlement.dto;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class BetDto {

    Long betId;
    Long userId;
    String eventId;
    String eventMarketId;
    String eventWinnerId;
    BigDecimal betAmount;
}
