package com.example.sportsbettingsettlement.domain;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class Bet {

    Long betId;
    Long userId;
    String eventId;
    String eventMarketId;
    String eventWinnerId;
    BigDecimal betAmount;

    public boolean hasWon(String eventWinnerId) {
        return this.eventWinnerId.equals(eventWinnerId);
    }
}