package com.example.sportsbettingsettlement.rocketmq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BetSettlementMessage {
    private Long betId;
    private Long userId;
    private String eventId;
    private String eventMarketId;
    private String eventWinnerId; // predicted winner
    private BigDecimal betAmount;
    private String actualEventWinnerId; // actual winner from outcome
    private final boolean won;
    private final BigDecimal payoutAmount;
}
