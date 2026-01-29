package com.example.sportsbettingsettlement.kafka;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.service.SportEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class KafkaConsumerTest {

    @Mock
    private SportEventService service;

    @InjectMocks
    private KafkaConsumer consumer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldConsumeObjectAndCallServiceOnConsume() {
        SportEventOutcome outcome = new SportEventOutcome("EVT-1", "Event One", "WIN-1");

        consumer.consume(outcome);

        verify(service, times(1)).handle(outcome);
    }
}
