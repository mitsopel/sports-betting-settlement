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
    String predictedEventWinnerId; // predicted winner
    String actualEventWinnerId; // actual winner from the sport event outcome
    BigDecimal betAmount;
    BigDecimal payoutAmount;

    public static BetSettlementMessage createBetSettlementMessage(Bet bet, SportEventOutcome sportEventOutcome) {
        return BetSettlementMessage.builder()
            .betId(bet.getBetId())
            .userId(bet.getUserId())
            .eventId(bet.getEventId())
            .eventMarketId(bet.getEventMarketId())
            .predictedEventWinnerId(bet.getEventWinnerId())
            .actualEventWinnerId(sportEventOutcome.getEventWinnerId())
            .betAmount(bet.getBetAmount())
            .payoutAmount(bet.getBetAmount().multiply(BigDecimal.valueOf(2)))
            .build();
    }
}
