package com.example.sportsbettingsettlement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;
import com.example.sportsbettingsettlement.mapper.SportEventOutcomeMapper;
import com.example.sportsbettingsettlement.service.SportEventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SportEventController.class)
class SportEventControllerTest {

    private static final String ENDPOINT_SPORT_EVENT_OUTCOMES = "/sport-event/outcomes";
    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String EVENT_ONE = "Event One";
    private static final String WINNER_ID_WIN_1 = "WIN-1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SportEventService sportEventService;

    @MockBean
    private SportEventOutcomeMapper sportEventOutcomeMapper;

    @Test
    void shouldAddSportEventOutcome() throws Exception {
        SportEventOutcomeDto sportEventOutcomeDto = new SportEventOutcomeDto(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1);
        SportEventOutcome sportEventOutcome = new SportEventOutcome(EVENT_ID_EVT_1, EVENT_ONE, WINNER_ID_WIN_1);
        when(sportEventOutcomeMapper.toDomain(any())).thenReturn(sportEventOutcome);
        doNothing().when(sportEventService).add(sportEventOutcome);

        mockMvc.perform(post(ENDPOINT_SPORT_EVENT_OUTCOMES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sportEventOutcomeDto)))
            .andExpect(status().isOk());
    }

    @Test
    void shouldAFailToAdSportEventOutcomeAndThrowBadRequestWhenDtoIsInvalid() throws Exception {
        // Missing eventId and eventWinnerId
        String invalidJson = "{\"eventName\":\"" + EVENT_ONE + "\"}";

        mockMvc.perform(post(ENDPOINT_SPORT_EVENT_OUTCOMES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
            .andExpect(status().isBadRequest());
    }
}
