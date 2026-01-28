package com.example.sportsbettingsettlement.rocketmq;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
//todo maybe rename??
public class BetSettlementProducer {

    public void send(Bet bet, SportEventOutcome sportEventOutcome) {

        boolean hasWon = bet.getEventWinnerId().equals(sportEventOutcome.eventWinnerId());
        BigDecimal payoutAmount = hasWon ? bet.getBetAmount().multiply(BigDecimal.valueOf(2)) : BigDecimal.ZERO;

        BetSettlementMessage betSettlementMessage = new BetSettlementMessage(
            bet.getBetId(),
            bet.getUserId(),
            bet.getEventId(),
            bet.getEventMarketId(),
            bet.getEventWinnerId(),
            bet.getBetAmount(),
            sportEventOutcome.eventWinnerId(),
            hasWon,
            payoutAmount
        );

        log.info("[MOCK ROCKETMQ] Sending to topic bet-settlements: {}", betSettlementMessage);
    }
}
