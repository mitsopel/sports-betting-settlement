package com.example.sportsbettingsettlement.kafka;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.json.JsonUtils;
import com.example.sportsbettingsettlement.service.SportEventOutcomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SportEventOutcomeKafkaConsumer {

    private final SportEventOutcomeService sportEventOutcomeService;
    private final JsonUtils jsonUtils;

    @KafkaListener(topics = "event-outcomes", groupId = "bet-settlement-group")
    public void consume(String payload) {
        SportEventOutcome sportEventOutcome = jsonUtils.parseJson(payload, SportEventOutcome.class);
        log.info("Received sportEventOutcome from Kafka: {}", sportEventOutcome);
        sportEventOutcomeService.handle(sportEventOutcome);
    }
}
