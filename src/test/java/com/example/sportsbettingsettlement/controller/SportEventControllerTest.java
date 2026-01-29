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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SportEventService service;

    @MockBean
    private SportEventOutcomeMapper mapper;

    @Test
    void shouldPublishOutcomeAndReturn200() throws Exception {
        SportEventOutcomeDto dto = new SportEventOutcomeDto("EVT-1", "Event One", "WIN-1");
        SportEventOutcome domain = new SportEventOutcome("EVT-1", "Event One", "WIN-1");
        when(mapper.toDomain(any())).thenReturn(domain);
        doNothing().when(service).publish(domain);

        mockMvc.perform(post("/sport-event/outcomes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());
    }
}
