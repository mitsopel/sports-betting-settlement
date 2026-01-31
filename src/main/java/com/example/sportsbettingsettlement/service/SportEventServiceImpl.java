package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SportEventServiceImpl implements SportEventService {

    private final KafkaProducer kafkaProducer;

    @Override
    public void add(SportEventOutcome sportEventOutcome) {
        kafkaProducer.publish(sportEventOutcome);
    }
}
