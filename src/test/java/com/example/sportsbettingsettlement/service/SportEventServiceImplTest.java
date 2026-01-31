package com.example.sportsbettingsettlement.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SportEventServiceImplTest {

    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String EVENT_ONE = "Event One";
    private static final String WINNER_ID_WIN_1 = "WIN-1";

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private SportEventServiceImpl sportEventService;

    @Test
    void shouldPublishSportEventOutcome() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1);

        doNothing().when(kafkaProducer).publish(sportEventOutcome);
        sportEventService.add(sportEventOutcome);

        verify(kafkaProducer, times(1)).publish(sportEventOutcome);
    }
}
