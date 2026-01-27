package com.example.sportsbettingsettlement.kafka;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.json.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;

@Component
@RequiredArgsConstructor
public class SportEventOutcomeKafkaProducer {

    private static final String TOPIC = "event-outcomes";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonUtils jsonUtils;

    public void publish(SportEventOutcome sportEventOutcome) {
            String payload = jsonUtils.convertToJson(sportEventOutcome);
            kafkaTemplate.send(TOPIC, sportEventOutcome.eventId(), payload);
    }
}
