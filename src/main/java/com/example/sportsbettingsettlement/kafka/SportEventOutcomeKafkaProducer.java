package com.example.sportsbettingsettlement.kafka;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.json.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SportEventOutcomeKafkaProducer {

    // todo move all strings to application properties
    private static final String TOPIC = "event-outcomes";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonUtils jsonUtils;

    // todo make kafka like CM and add logs
    public void publish(SportEventOutcome sportEventOutcome) {
            String sportEventOutcomeJson = jsonUtils.convertToJson(sportEventOutcome);
            kafkaTemplate.send(TOPIC, sportEventOutcome.eventId(), sportEventOutcomeJson);
    }
}
