package com.example.sportsbettingsettlement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.domain.BetSettlementMessage;
import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.entity.BetEntity;
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
class BetServiceImplTest {

    private static final long BET_ID_1 = 1L;
    private static final long BET_ID_2 = 2L;
    private static final long USER_ID_101 = 101L;
    private static final long USER_ID_102 = 102L;
    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String EVENT_ID_EVT_2 = "EVT-2";
    private static final String EVENT_ONE = "Event One";
    private static final String MARKET_ID_MKT_1 = "MKT-1";
    private static final String MARKET_ID_MKT_2 = "MKT-2";
    private static final String WINNER_ID_WIN_1 = "WIN-1";
    private static final String WINNER_ID_WIN_2 = "WIN-2";
    private static final String WINNER_ID_WIN_3 = "WIN-3";
    private static final BigDecimal BET_AMOUNT_TEN = BigDecimal.TEN;
    private static final BigDecimal BET_AMOUNT_TWENTY = BigDecimal.valueOf(20);
    private static final BigDecimal EXPECTED_PAYOUT_TWENTY = BigDecimal.valueOf(20);

    @Mock
    private BetRepository betRepository;

    @Mock
    private RocketMQProducer rocketMQProducer;

    @Spy
    private BetMapper betMapper = Mappers.getMapper(BetMapper.class);

    @InjectMocks
    private BetServiceImpl betService;

    @Test
    void shouldFindAllBets() {
        BetEntity betEntity1 = new BetEntity(BET_ID_1, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1,
            BigDecimal.TEN);
        BetEntity betEntity2 = new BetEntity(BET_ID_2, USER_ID_102, EVENT_ID_EVT_2, MARKET_ID_MKT_2, WINNER_ID_WIN_2,
            BigDecimal.ONE);
        when(betRepository.findAll()).thenReturn(List.of(betEntity1, betEntity2));

        List<Bet> bets = betService.findAll();

        assertThat(bets).hasSize(2);
        assertThat(bets.get(0).getBetId()).isEqualTo(BET_ID_1);
        assertThat(bets.get(1).getEventId()).isEqualTo(EVENT_ID_EVT_2);
    }

    @Test
    void shouldReturnEmptyListWhenNoBets() {
        when(betRepository.findAll()).thenReturn(List.of());

        List<Bet> bets = betService.findAll();

        assertThat(bets).isEmpty();
    }

    @Test
    void shouldHandleSettlementsForSportEventOutcome() {
        BetEntity winner = new BetEntity(BET_ID_1, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1,
            BET_AMOUNT_TEN);
        BetEntity loser = new BetEntity(BET_ID_2, USER_ID_102, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_2,
            BET_AMOUNT_TWENTY);
        when(betRepository.findByEventId(EVENT_ID_EVT_1)).thenReturn(List.of(winner, loser));

        ArgumentCaptor<BetSettlementMessage> captor = ArgumentCaptor.forClass(BetSettlementMessage.class);

        betService.handleSettlements(new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1));

        verify(rocketMQProducer, times(1)).send(captor.capture());
        BetSettlementMessage betSettlementMessage = captor.getValue();
        assertThat(betSettlementMessage.getBetId()).isEqualTo(BET_ID_1);
        assertThat(betSettlementMessage.getEventWinnerId()).isEqualTo(WINNER_ID_WIN_1);
        assertThat(betSettlementMessage.getPayoutAmount()).isEqualByComparingTo(EXPECTED_PAYOUT_TWENTY);
    }

    @Test
    void shouldNotSendAnySettlementForSportEventOutcomeWhenNoWinners() {
        BetEntity loser1 = new BetEntity(BET_ID_1, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_2,
            BET_AMOUNT_TEN);
        BetEntity loser2 = new BetEntity(BET_ID_2, USER_ID_102, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_3,
            BET_AMOUNT_TWENTY);
        when(betRepository.findByEventId(EVENT_ID_EVT_1)).thenReturn(List.of(loser1, loser2));

        betService.handleSettlements(new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1));

        verifyNoInteractions(rocketMQProducer);
    }
}
