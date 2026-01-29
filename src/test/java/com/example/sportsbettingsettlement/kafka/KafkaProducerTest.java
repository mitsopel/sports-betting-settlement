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
    private KafkaTemplate<String, SportEventOutcome> sportEventOutcomeKafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(kafkaProducer, "eventOutcomesTopic", "event-outcomes");
    }

    @Test
    void shouldPublishSportEventOutcome() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome("EVT-1", "Event One", "WIN-1");
        when(sportEventOutcomeKafkaTemplate.send(eq("event-outcomes"), eq("EVT-1"), eq(sportEventOutcome)))
            .thenReturn(CompletableFuture.completedFuture(null));

        kafkaProducer.publish(sportEventOutcome);

        verify(sportEventOutcomeKafkaTemplate, times(1)).send(eq("event-outcomes"), eq("EVT-1"), eq(sportEventOutcome));
    }

    @Test
    void shouldFailToPublishSportEventOutcomeDueToKafkaAndCatchExceptionInternally() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome("EVT-1", "Event One", "WIN-1");
        // Make the template throw synchronously
        when(sportEventOutcomeKafkaTemplate.send(eq("event-outcomes"), eq("EVT-1"), eq(sportEventOutcome)))
            .thenThrow(new org.apache.kafka.common.KafkaException("boom"));

        // Should catch exception internally and not throw
        kafkaProducer.publish(sportEventOutcome);

        verify(sportEventOutcomeKafkaTemplate, times(1)).send(eq("event-outcomes"), eq("EVT-1"), eq(sportEventOutcome));
    }

    @Test
    void shouldFailToPublishSportEventOutcomeDueToAsyncFailureAndCatchExceptionInternally() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome("EVT-1", "Event One", "WIN-1");
        CompletableFuture<Object> failed = new CompletableFuture<>();
        failed.completeExceptionally(new RuntimeException("async failure"));
        when(sportEventOutcomeKafkaTemplate.send(eq("event-outcomes"), eq("EVT-1"), eq(sportEventOutcome)))
            .thenReturn((CompletableFuture) failed);

        kafkaProducer.publish(sportEventOutcome);

        verify(sportEventOutcomeKafkaTemplate, times(1)).send(eq("event-outcomes"), eq("EVT-1"), eq(sportEventOutcome));
    }
}
