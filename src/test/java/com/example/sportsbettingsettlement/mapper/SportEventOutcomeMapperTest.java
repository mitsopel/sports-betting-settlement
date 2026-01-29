package com.example.sportsbettingsettlement.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class SportEventOutcomeMapperTest {

    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String EVENT_NAME_EVENT_ONE = "Event One";
    private static final String WINNER_ID_WIN_1 = "WIN-1";

    private final SportEventOutcomeMapper sportEventOutcomeMapper = Mappers.getMapper(SportEventOutcomeMapper.class);

    @Test
    void shouldMapAllFieldsToDomain() {
        SportEventOutcomeDto sportEventOutcomeDto = new SportEventOutcomeDto(EVENT_ID_EVT_1, EVENT_NAME_EVENT_ONE,
            WINNER_ID_WIN_1);
        SportEventOutcome sportEventOutcome = sportEventOutcomeMapper.toDomain(sportEventOutcomeDto);
        assertThat(sportEventOutcome.getEventId()).isEqualTo(EVENT_ID_EVT_1);
        assertThat(sportEventOutcome.getEventName()).isEqualTo(EVENT_NAME_EVENT_ONE);
        assertThat(sportEventOutcome.getEventWinnerId()).isEqualTo(WINNER_ID_WIN_1);
    }
}
