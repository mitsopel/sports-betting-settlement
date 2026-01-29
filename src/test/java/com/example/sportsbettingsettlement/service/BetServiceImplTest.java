package com.example.sportsbettingsettlement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.mapper.BetMapper;
import com.example.sportsbettingsettlement.entity.BetEntity;
import com.example.sportsbettingsettlement.repository.BetRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
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
    private static final String MARKET_ID_MKT_1 = "MKT-1";
    private static final String MARKET_ID_MKT_2 = "MKT-2";
    private static final String WINNER_ID_WIN_1 = "WIN-1";
    private static final String WINNER_ID_WIN_2 = "WIN-2";

    @Mock
    private BetRepository betRepository;

    @Spy
    private BetMapper betMapper = Mappers.getMapper(BetMapper.class);

    @InjectMocks
    private BetServiceImpl betService;

    @Test
    void shouldFindAllBets() {
        BetEntity betEntity1 = new BetEntity(BET_ID_1, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1, BigDecimal.TEN);
        BetEntity betEntity2 = new BetEntity(BET_ID_2, USER_ID_102, EVENT_ID_EVT_2, MARKET_ID_MKT_2, WINNER_ID_WIN_2, BigDecimal.ONE);
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
}
