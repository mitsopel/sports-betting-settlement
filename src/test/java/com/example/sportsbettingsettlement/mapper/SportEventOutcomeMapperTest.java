package com.example.sportsbettingsettlement.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class SportEventOutcomeMapperTest {

    private final SportEventOutcomeMapper mapper = Mappers.getMapper(SportEventOutcomeMapper.class);

    @Test
    void shouldMapAllFieldsToDomain() {
        SportEventOutcomeDto dto = new SportEventOutcomeDto("EVT-1", "Event One", "WIN-1");
        SportEventOutcome domain = mapper.toDomain(dto);
        assertThat(domain.getEventId()).isEqualTo("EVT-1");
        assertThat(domain.getEventName()).isEqualTo("Event One");
        assertThat(domain.getEventWinnerId()).isEqualTo("WIN-1");
    }
}
