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
    private SportEventService sportEventService;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldConsumeSportEventOutcome() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome("EVT-1", "Event One", "WIN-1");

        kafkaConsumer.consume(sportEventOutcome);

        verify(sportEventService, times(1)).handle(sportEventOutcome);
    }
}
