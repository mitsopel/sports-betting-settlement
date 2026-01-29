package com.example.sportsbettingsettlement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.sportsbettingsettlement.domain.BetSettlementMessage;
import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.entity.BetEntity;
import com.example.sportsbettingsettlement.kafka.KafkaProducer;
import com.example.sportsbettingsettlement.mapper.BetMapper;
import com.example.sportsbettingsettlement.repository.BetRepository;
import com.example.sportsbettingsettlement.rocketmq.RocketMQProducer;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SportEventOutcomeServiceImplTest {

    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String EVENT_ONE = "Event One";
    private static final String MARKET_ID_MKT_1 = "MKT-1";
    private static final String WINNER_ID_WIN_1 = "WIN-1";
    private static final String WINNER_ID_WIN_2 = "WIN-2";
    private static final String WINNER_ID_WIN_3 = "WIN-3";

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private BetRepository betRepository;

    @Mock
    private RocketMQProducer rocketMQProducer;

    @Spy
    private BetMapper betMapper = Mappers.getMapper(BetMapper.class);

    @InjectMocks
    private SportEventServiceImpl sportEventService;

    @Test
    void shouldPublishSportEventOutcome() {
        SportEventOutcome sportEventOutcome = new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1);

        doNothing().when(kafkaProducer).publish(sportEventOutcome);
        sportEventService.publish(sportEventOutcome);

        verify(kafkaProducer, times(1)).publish(sportEventOutcome);
    }

    @Test
    void shouldHandleSettlementsForSportEventOutcome() {
        BetEntity winner = new BetEntity(1L, 101L, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1, BigDecimal.TEN);
        BetEntity loser = new BetEntity(2L, 102L, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_2,
            BigDecimal.valueOf(20));
        when(betRepository.findByEventId(EVENT_ID_EVT_1)).thenReturn(List.of(winner, loser));

        ArgumentCaptor<BetSettlementMessage> captor = ArgumentCaptor.forClass(BetSettlementMessage.class);
        doNothing().when(rocketMQProducer).send(any());

        sportEventService.handleSettlements(new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1));

        verify(rocketMQProducer, times(1)).send(captor.capture());
        BetSettlementMessage betSettlementMessage = captor.getValue();
        assertThat(betSettlementMessage.getBetId()).isEqualTo(1L);
        assertThat(betSettlementMessage.getEventWinnerId()).isEqualTo(WINNER_ID_WIN_1);
        assertThat(betSettlementMessage.getPayoutAmount()).isEqualByComparingTo(BigDecimal.valueOf(20));
    }

    @Test
    void shouldNotSendAnySettlementForSportEventOutcomeWhenNoWinners() {
        BetEntity loser1 = new BetEntity(1L, 101L, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_2, BigDecimal.TEN);
        BetEntity loser2 = new BetEntity(2L, 102L, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_3,
            BigDecimal.valueOf(20));
        when(betRepository.findByEventId(EVENT_ID_EVT_1)).thenReturn(List.of(loser1, loser2));

        sportEventService.handleSettlements(new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1));

        verify(rocketMQProducer, times(0)).send(any());
    }
}
