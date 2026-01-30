package com.example.sportsbettingsettlement.rocketmq;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.sportsbettingsettlement.domain.BetSettlementMessage;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class RocketMQProducerTest {

    private static final String TOPIC_BET_SETTLEMENTS = "bet-settlements";
    private static final long BET_ID_1 = 1L;
    private static final long USER_ID_101 = 101L;
    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String MARKET_ID_MKT_1 = "MKT-1";
    private static final String WINNER_ID_WIN_1 = "WIN-1";
    private static final BigDecimal BET_AMOUNT_TEN = BigDecimal.TEN;
    private static final BigDecimal PAYOUT_AMOUNT_TWENTY = BigDecimal.valueOf(20);

    @InjectMocks
    private RocketMQProducer rocketMQProducer;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(rocketMQProducer, "betSettlementsTopic", TOPIC_BET_SETTLEMENTS);
    }

    @Test
    void shouldSendBetSettlementMessage() {
        BetSettlementMessage betSettlementMessage = BetSettlementMessage.builder()
            .betId(BET_ID_1)
            .userId(USER_ID_101)
            .eventId(EVENT_ID_EVT_1)
            .eventMarketId(MARKET_ID_MKT_1)
            .eventWinnerId(WINNER_ID_WIN_1)
            .betAmount(BET_AMOUNT_TEN)
            .payoutAmount(PAYOUT_AMOUNT_TWENTY)
            .build();

        assertThatCode(() -> rocketMQProducer.send(betSettlementMessage)).doesNotThrowAnyException();
    }
}
