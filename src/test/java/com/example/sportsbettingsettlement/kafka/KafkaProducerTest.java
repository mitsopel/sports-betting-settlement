package com.example.sportsbettingsettlement.kafka;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, SportEventOutcome> kafkaTemplate;

    @InjectMocks
    private KafkaProducer producer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(producer, "eventOutcomesTopic", "event-outcomes");
    }

    @Test
    void shouldPublishSendingToKafkaWithKeyAndObject() {
        SportEventOutcome outcome = new SportEventOutcome("EVT-1", "Event One", "WIN-1");
        when(kafkaTemplate.send(eq("event-outcomes"), eq("EVT-1"), eq(outcome)))
            .thenReturn(CompletableFuture.completedFuture(null));

        producer.publish(outcome);

        verify(kafkaTemplate, times(1)).send(eq("event-outcomes"), eq("EVT-1"), eq(outcome));
    }
}
