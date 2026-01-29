package com.example.sportsbettingsettlement.kafka;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.service.SportEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

    @Mock
    private SportEventService sportEventService;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Test
    void shouldConsumeSportEventOutcome() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome("EVT-1", "Event One", "WIN-1");

        kafkaConsumer.consume(sportEventOutcome);

        verify(sportEventService, times(1)).handleSettlements(sportEventOutcome);
    }
}
