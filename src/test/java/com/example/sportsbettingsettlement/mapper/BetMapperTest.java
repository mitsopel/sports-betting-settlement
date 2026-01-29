package com.example.sportsbettingsettlement.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.dto.BetDto;
import com.example.sportsbettingsettlement.entity.BetEntity;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class BetMapperTest {

    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String EVENT_ID_EVT_2 = "EVT-2";
    private static final String MARKET_ID_MKT_1 = "MKT-1";
    private static final String MARKET_ID_MKT_2 = "MKT-2";
    private static final String WINNER_ID_WIN_1 = "WIN-1";
    private static final String WINNER_ID_WIN_2 = "WIN-2";
    private static final long BET_ID_1 = 1L;
    private static final long BET_ID_2 = 2L;
    private static final long USER_ID_101 = 101L;
    private static final long USER_ID_102 = 102L;

    private final BetMapper betMapper = Mappers.getMapper(BetMapper.class);

    @Test
    void shouldMapEntityToDomainAndDomainToDtoRoundTrip() {
        BetEntity betEntity = new BetEntity(BET_ID_1, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1,
            BigDecimal.TEN);
        Bet bet = betMapper.toDomain(betEntity);
        BetDto betDto = betMapper.toDto(bet);

        assertThat(bet.getBetId()).isEqualTo(betEntity.getBetId());
        assertThat(betDto.getUserId()).isEqualTo(bet.getUserId());
        assertThat(betDto.getEventId()).isEqualTo(EVENT_ID_EVT_1);
        assertThat(betDto.getBetAmount()).isEqualByComparingTo(BigDecimal.TEN);
    }

    @Test
    void shouldMapEntityListToDomainListAndDomainListToDtoList() {
        BetEntity betEntity1 = new BetEntity(BET_ID_1, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1,
            BigDecimal.TEN);
        BetEntity betEntity2 = new BetEntity(BET_ID_2, USER_ID_102, EVENT_ID_EVT_2, MARKET_ID_MKT_2, WINNER_ID_WIN_2,
            BigDecimal.ONE);

        List<Bet> bets = betMapper.toDomainList(List.of(betEntity1, betEntity2));
        List<BetDto> betDtos = betMapper.toDtoList(bets);

        assertThat(bets).hasSize(2);
        assertThat(betDtos).extracting(BetDto::getBetId).containsExactly(BET_ID_1, BET_ID_2);
    }
}
