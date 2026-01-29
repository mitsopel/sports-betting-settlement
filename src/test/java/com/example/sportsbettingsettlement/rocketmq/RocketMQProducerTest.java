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

    @InjectMocks
    private RocketMQProducer rocketMQProducer;

    @BeforeEach
    void setup() {
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
