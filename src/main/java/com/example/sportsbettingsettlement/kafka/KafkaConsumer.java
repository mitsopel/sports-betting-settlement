package com.example.sportsbettingsettlement.kafka;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.json.JsonUtils;
import com.example.sportsbettingsettlement.service.SportEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final SportEventService sportEventService;
    private final JsonUtils jsonUtils;

    @KafkaListener(topics = "${kafka.topics.event-outcomes}", groupId = "${kafka.consumer.group-id}")
    public void consume(String sportEventOutcomeJson) {
        SportEventOutcome sportEventOutcome = jsonUtils.parseJson(sportEventOutcomeJson, SportEventOutcome.class);
        log.info("Received sportEventOutcome from Kafka: {}", sportEventOutcome);
        sportEventService.handle(sportEventOutcome);
    }
}
