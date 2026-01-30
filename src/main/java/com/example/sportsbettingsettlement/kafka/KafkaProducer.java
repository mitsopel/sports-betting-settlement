package com.example.sportsbettingsettlement.kafka;

import static java.util.Objects.isNull;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, SportEventOutcome> sportEventOutcomeKafkaTemplate;

    @Value("${kafka.topics.event-outcomes}")
    private String eventOutcomesTopic;

    public void publish(SportEventOutcome sportEventOutcome) {
        try {
            sportEventOutcomeKafkaTemplate.send(eventOutcomesTopic, sportEventOutcome.getEventId(), sportEventOutcome)
                .whenCompleteAsync((response, exception) -> {
                    if (isNull(exception)) {
                        log.info("Sent sportEventOutcome to Kafka: {}", sportEventOutcome);
                    } else {
                        logErrorMessage(sportEventOutcome, exception);
                    }
                });
        } catch (KafkaException exception) {
            logErrorMessage(sportEventOutcome, exception);
        }
    }

    private void logErrorMessage(final Object message, final Throwable exception) {
        log.error("Failed to send message to Kafka {}", message, exception);
    }
}
