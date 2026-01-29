package com.example.sportsbettingsettlement.rocketmq;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.sportsbettingsettlement.domain.BetSettlementMessage;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

class RocketMQProducerTest {

    @InjectMocks
    private RocketMQProducer rocketMQProducer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(rocketMQProducer, "betSettlementsTopic", "bet-settlements");
    }

    @Test
    void shouldSendBetSettlementMessage() {
        BetSettlementMessage betSettlementMessage = BetSettlementMessage.builder()
            .betId(1L)
            .userId(101L)
            .eventId("EVT-1")
            .eventMarketId("MKT-1")
            .eventWinnerId("WIN-1")
            .betAmount(BigDecimal.TEN)
            .payload(BigDecimal.valueOf(20))
            .build();

        assertThatCode(() -> rocketMQProducer.send(betSettlementMessage)).doesNotThrowAnyException();
    }
}
