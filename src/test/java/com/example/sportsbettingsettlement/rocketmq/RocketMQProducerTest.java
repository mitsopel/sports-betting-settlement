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
    private RocketMQProducer producer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(producer, "betSettlementsTopic", "bet-settlements");
    }

    @Test
    void shouldNotThrowAndLogsOnSend() {
        BetSettlementMessage msg = BetSettlementMessage.builder()
            .betId(1L)
            .userId(101L)
            .eventId("EVT-1")
            .eventMarketId("MKT-1")
            .eventWinnerId("WIN-1")
            .betAmount(BigDecimal.TEN)
            .payload(BigDecimal.valueOf(20))
            .build();

        assertThatCode(() -> producer.send(msg)).doesNotThrowAnyException();
    }
}
