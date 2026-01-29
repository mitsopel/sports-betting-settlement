package com.example.sportsbettingsettlement.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class BetSettlementMessage {

    Long betId;
    Long userId;
    String eventId;
    String eventMarketId;
    String eventWinnerId;
    BigDecimal betAmount;
    BigDecimal payload;

    public static BetSettlementMessage createBetSettlementMessage(Bet bet) {
        return BetSettlementMessage.builder()
            .betId(bet.getBetId())
            .userId(bet.getUserId())
            .eventId(bet.getEventId())
            .eventMarketId(bet.getEventMarketId())
            .eventWinnerId(bet.getEventWinnerId())
            .betAmount(bet.getBetAmount())
            .payload(computePayload(bet.getBetAmount()))
            .build();
    }

    private static BigDecimal computePayload(BigDecimal betAmount) {
        // double the bet amount
        return betAmount.multiply(BigDecimal.valueOf(2));
    }
}
