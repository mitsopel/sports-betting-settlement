package com.example.sportsbettingsettlement.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class SportEventOutcomeMapperTest {

    private final SportEventOutcomeMapper sportEventOutcomeMapper = Mappers.getMapper(SportEventOutcomeMapper.class);

    @Test
    void shouldMapAllFieldsToDomain() {
        SportEventOutcomeDto sportEventOutcomeDto = new SportEventOutcomeDto("EVT-1", "Event One", "WIN-1");
        SportEventOutcome sportEventOutcome = sportEventOutcomeMapper.toDomain(sportEventOutcomeDto);
        assertThat(sportEventOutcome.getEventId()).isEqualTo("EVT-1");
        assertThat(sportEventOutcome.getEventName()).isEqualTo("Event One");
        assertThat(sportEventOutcome.getEventWinnerId()).isEqualTo("WIN-1");
    }
}
