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

    private final BetMapper betMapper = Mappers.getMapper(BetMapper.class);

    @Test
    void shouldMapEntityToDomainAndDomainToDtoRoundTrip() {
        BetEntity betEntity = new BetEntity(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN);
        Bet bet = betMapper.toDomain(betEntity);
        BetDto betDto = betMapper.toDto(bet);

        assertThat(bet.getBetId()).isEqualTo(betEntity.getBetId());
        assertThat(betDto.getUserId()).isEqualTo(bet.getUserId());
        assertThat(betDto.getEventId()).isEqualTo("EVT-1");
        assertThat(betDto.getBetAmount()).isEqualByComparingTo(BigDecimal.TEN);
    }

    @Test
    void shouldMapEntityListToDomainListAndDomainListToDtoList() {
        BetEntity betEntity1 = new BetEntity(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN);
        BetEntity betEntity2 = new BetEntity(2L, 102L, "EVT-2", "MKT-2", "WIN-2", BigDecimal.ONE);

        List<Bet> bets = betMapper.toDomainList(List.of(betEntity1, betEntity2));
        List<BetDto> betDtos = betMapper.toDtoList(bets);

        assertThat(bets).hasSize(2);
        assertThat(betDtos).extracting(BetDto::getBetId).containsExactly(1L, 2L);
    }
}
