package com.example.sportsbettingsettlement.rocketmq;

import static org.assertj.core.api.Assertions.assertThatCode;

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
        BetSettlementMessage msg = new BetSettlementMessage(
            1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN, "WIN-1", BigDecimal.valueOf(20)
        );

        assertThatCode(() -> producer.send(msg)).doesNotThrowAnyException();
    }
}
