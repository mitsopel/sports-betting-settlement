package com.example.sportsbettingsettlement.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.dto.BetDto;
import com.example.sportsbettingsettlement.persistence.BetEntity;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class BetMapperTest {

    private final BetMapper mapper = Mappers.getMapper(BetMapper.class);

    @Test
    void shouldMapEntityToDomainAndDomainToDtoRoundTrip() {
        BetEntity entity = new BetEntity(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN);
        Bet domain = mapper.toDomain(entity);
        BetDto dto = mapper.toDto(domain);

        assertThat(domain.getBetId()).isEqualTo(entity.getBetId());
        assertThat(dto.getUserId()).isEqualTo(domain.getUserId());
        assertThat(dto.getEventId()).isEqualTo("EVT-1");
        assertThat(dto.getBetAmount()).isEqualByComparingTo(BigDecimal.TEN);
    }

    @Test
    void shouldMapEntityListToDomainListAndDomainListToDtoList() {
        var e1 = new BetEntity(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN);
        var e2 = new BetEntity(2L, 102L, "EVT-2", "MKT-2", "WIN-2", BigDecimal.ONE);

        List<Bet> domains = mapper.toDomainList(List.of(e1, e2));
        List<BetDto> dtos = mapper.toDtoList(domains);

        assertThat(domains).hasSize(2);
        assertThat(dtos).extracting(BetDto::getBetId).containsExactly(1L, 2L);
    }
}
