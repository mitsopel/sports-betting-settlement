package com.example.sportsbettingsettlement.kafka;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.json.JsonUtils;
import com.example.sportsbettingsettlement.service.SportEventOutcomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class KafkaConsumerTest {

    @Mock
    private SportEventOutcomeService service;

    @Mock
    private JsonUtils jsonUtils;

    @InjectMocks
    private KafkaConsumer consumer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldParseJsonAndCallServiceOnConsume() {
        String payload = "{\"eventId\":\"EVT-1\",\"eventName\":\"Event One\",\"eventWinnerId\":\"WIN-1\"}";
        SportEventOutcome outcome = new SportEventOutcome("EVT-1", "Event One", "WIN-1");
        when(jsonUtils.parseJson(payload, SportEventOutcome.class)).thenReturn(outcome);

        consumer.consume(payload);

        verify(service, times(1)).handle(outcome);
    }
}
