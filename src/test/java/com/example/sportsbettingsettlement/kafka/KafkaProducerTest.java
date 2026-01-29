package com.example.sportsbettingsettlement.kafka;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTest {

    private static final String TOPIC_EVENT_OUTCOMES = "event-outcomes";
    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String EVENT_ONE = "Event One";
    private static final String WINNER_ID_WIN_1 = "WIN-1";

    @Mock
    private KafkaTemplate<String, SportEventOutcome> sportEventOutcomeKafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(kafkaProducer, "eventOutcomesTopic", TOPIC_EVENT_OUTCOMES);
    }

    @Test
    void shouldPublishSportEventOutcome() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1);
        when(sportEventOutcomeKafkaTemplate.send(eq(TOPIC_EVENT_OUTCOMES), eq(EVENT_ID_EVT_1), eq(sportEventOutcome)))
            .thenReturn(CompletableFuture.completedFuture(null));

        kafkaProducer.publish(sportEventOutcome);

        verify(sportEventOutcomeKafkaTemplate, times(1)).send(eq(TOPIC_EVENT_OUTCOMES), eq(EVENT_ID_EVT_1),
            eq(sportEventOutcome));
    }

    @Test
    void shouldFailToPublishSportEventOutcomeDueToKafkaAndCatchExceptionInternally() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1);
        // Make the template throw synchronously
        when(sportEventOutcomeKafkaTemplate.send(eq(TOPIC_EVENT_OUTCOMES), eq(EVENT_ID_EVT_1), eq(sportEventOutcome)))
            .thenThrow(new org.apache.kafka.common.KafkaException("boom"));

        // Should catch exception internally and not throw
        kafkaProducer.publish(sportEventOutcome);

        verify(sportEventOutcomeKafkaTemplate, times(1)).send(eq(TOPIC_EVENT_OUTCOMES), eq(EVENT_ID_EVT_1),
            eq(sportEventOutcome));
    }

    @Test
    void shouldFailToPublishSportEventOutcomeDueToAsyncFailureAndCatchExceptionInternally() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1);
        CompletableFuture<Object> failed = new CompletableFuture<>();
        failed.completeExceptionally(new RuntimeException("async failure"));
        when(sportEventOutcomeKafkaTemplate.send(eq(TOPIC_EVENT_OUTCOMES), eq(EVENT_ID_EVT_1), eq(sportEventOutcome)))
            .thenReturn((CompletableFuture) failed);

        kafkaProducer.publish(sportEventOutcome);

        verify(sportEventOutcomeKafkaTemplate, times(1)).send(eq(TOPIC_EVENT_OUTCOMES), eq(EVENT_ID_EVT_1),
            eq(sportEventOutcome));
    }
}
