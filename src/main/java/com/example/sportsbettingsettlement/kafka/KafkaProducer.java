package com.example.sportsbettingsettlement.kafka;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.json.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    @Value("${kafka.topics.event-outcomes}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonUtils jsonUtils;

    public void publish(SportEventOutcome sportEventOutcome) {
        String sportEventOutcomeJson = jsonUtils.convertToJson(sportEventOutcome);
        kafkaTemplate.send(topic, sportEventOutcome.getEventId(), sportEventOutcomeJson);
        log.info("Sent sportEventOutcome to Kafka: {}", sportEventOutcome);
    }
}
