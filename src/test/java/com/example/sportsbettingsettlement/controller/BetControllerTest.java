package com.example.sportsbettingsettlement.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.dto.BetDto;
import com.example.sportsbettingsettlement.mapper.BetMapper;
import com.example.sportsbettingsettlement.service.BetService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BetController.class)
class BetControllerTest {

    private static final String ENDPOINT_BETS = "/bets";
    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String EVENT_ID_EVT_2 = "EVT-2";
    private static final String MARKET_ID_MKT_1 = "MKT-1";
    private static final String MARKET_ID_MKT_2 = "MKT-2";
    private static final String WINNER_ID_WIN_1 = "WIN-1";
    private static final String WINNER_ID_WIN_2 = "WIN-2";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BetService betService;

    @MockBean
    private BetMapper betMapper;

    @Test
    void shouldFindAllBets() throws Exception {
        List<Bet> betList = List.of(
            new Bet(1L, 101L, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1, BigDecimal.TEN),
            new Bet(2L, 102L, EVENT_ID_EVT_2, MARKET_ID_MKT_2, WINNER_ID_WIN_2, BigDecimal.ONE)
        );
        when(betService.findAll()).thenReturn(betList);
        when(betMapper.toDtoList(betList)).thenReturn(List.of(
            new BetDto(1L, 101L, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1, BigDecimal.TEN),
            new BetDto(2L, 102L, EVENT_ID_EVT_2, MARKET_ID_MKT_2, WINNER_ID_WIN_2, BigDecimal.ONE)
        ));

        mockMvc.perform(get(ENDPOINT_BETS))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].betId").value(1))
            .andExpect(jsonPath("$[1].eventId").value(EVENT_ID_EVT_2));
    }
}
